import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class WishlistService {

    private wishlist: any[] = [];
    private readonly API_BASE_URL = '/api/wishlist/me';

    constructor(private http: HttpClient) {
        this.caricaDaBackend();
    }

    private caricaDaBackend() {
        this.http.get<any>(this.API_BASE_URL).subscribe({
            next: (res) => {
                this.aggiornaLista(res.prodotti);
            },
            error: (err) => console.error('Errore durante il caricamento della wishlist dal DB', err)
        });
    }

    getProdotti() {
        return this.wishlist;
    }

    aggiungi(prodotto: any) {
        const giaPresente = this.wishlist.find(p => p.id === prodotto.id);
        if (!giaPresente) {
            // Chiamata POST: il body è vuoto perché passiamo l'ID nell'URL come da specifiche API
            this.http.post<any>(`${this.API_BASE_URL}/prodotti/${prodotto.id}`, {}).subscribe({
                next: (res) => {
                    this.aggiornaLista(res.prodotti);
                },
                error: (err) => console.error('Errore durante l\'aggiunta del prodotto alla wishlist', err)
            });
        }
    }

    rimuovi(id: number) {
        // Chiamata DELETE: passiamo l'ID nell'URL
        this.http.delete<any>(`${this.API_BASE_URL}/prodotti/${id}`).subscribe({
            next: (res) => {
                this.aggiornaLista(res.prodotti);
            },
            error: (err) => console.error('Errore durante la rimozione del prodotto dalla wishlist', err)
        });
    }

    isInWishlist(id: number): boolean {
        return this.wishlist.some(p => p.id === id);
    }

    /**
     * Metodo di supporto per mappare le chiavi del JSON del backend 
     * con le proprietà lette dal tuo HTML (es. nome -> name, prezzo -> price).
     */
    private aggiornaLista(prodottiBackend: any[]) {
        if (!prodottiBackend) {
            this.wishlist = [];
            return;
        }
        
        this.wishlist = prodottiBackend.map(p => ({
            ...p,
            name: p.nome || p.name,
            price: p.prezzo || p.price,
            imageUrl: p.immagine || p.imageUrl
        }));
    }
}