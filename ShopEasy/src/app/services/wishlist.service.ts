import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class WishlistService {

    private wishlist: any[] = [];

    constructor() {
        this.caricaDaStorage();
    }

    private caricaDaStorage() {
        const salvata = localStorage.getItem('shopeasy-wishlist');
        if (salvata) {
            this.wishlist = JSON.parse(salvata);
        }
    }

    private salvaInStorage() {
        localStorage.setItem('shopeasy-wishlist', JSON.stringify(this.wishlist));
    }

    getProdotti() {
        return this.wishlist;
    }

    aggiungi(prodotto: any) {
        const giaPresente = this.wishlist.find(p => p.id === prodotto.id);
        if (!giaPresente) {
            this.wishlist.push(prodotto);
            this.salvaInStorage();
        }
    }

    rimuovi(id: number) {
        this.wishlist = this.wishlist.filter(p => p.id !== id);
        this.salvaInStorage();
    }

    isInWishlist(id: number): boolean {
        return this.wishlist.some(p => p.id === id);
    }
}