import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class WishlistService {

    // 1. Canale privato reattivo (Signal)
    private wishlistSignal = signal<any[]>([]);


    private readonly API_BASE_URL = '/api/wishlist/me';
    // private readonly API_BASE_URL = '/api/wishlist/me'; // Usa questa se usi il proxy.conf.json

    // 2. Altoparlante pubblico per l'HTML
    public readonly prodotti = computed(() => this.wishlistSignal());

    constructor(private http: HttpClient) {
        this.caricaDaBackend();
    }

    private caricaDaBackend() {
        this.http.get<any>(this.API_BASE_URL).subscribe({
            next: (res) => {
                // Riconosce automaticamente se il server manda un oggetto o un array puro
                const dati = res?.prodotti ? res.prodotti : (Array.isArray(res) ? res : null);
                this.aggiornaLista(dati);
            },
            error: (err) => console.error('Errore durante il caricamento della wishlist dal DB', err)
        });
    }

    aggiungi(prodotto: any) {
        const giaPresente = this.wishlistSignal().some(p => p.id === prodotto.id);
        if (!giaPresente) {
            this.http.post<any>(`${this.API_BASE_URL}/prodotti/${prodotto.id}`, {}).subscribe({
                next: (res) => {
                    const dati = res?.prodotti ? res.prodotti : (Array.isArray(res) ? res : null);
                    this.aggiornaLista(dati);
                },
                error: (err) => console.error('Errore durante l\'aggiunta del prodotto alla wishlist', err)
            });
        }
    }

    rimuovi(id: number) {
        this.http.delete<any>(`${this.API_BASE_URL}/prodotti/${id}`).subscribe({
            next: (res) => {
                const dati = res?.prodotti ? res.prodotti : (Array.isArray(res) ? res : null);
                this.aggiornaLista(dati);
            },
            error: (err) => console.error('Errore durante la rimozione del prodotto dalla wishlist', err)
        });
    }

    isInWishlist(id: number): boolean {
        return this.wishlistSignal().some(p => p.id === id);
    }

    private aggiornaLista(prodottiBackend: any[] | null) {
        if (!prodottiBackend) {
            this.wishlistSignal.set([]);
            return;
        }

        // Mappa i campi del DB italiano/inglese per renderli compatibili con il tuo HTML
        const mappati = prodottiBackend.map(p => ({
            ...p,
            name: p.nome || p.name,
            price: p.prezzo || p.price,
            imageUrl: p.immagine || p.imageUrl
        }));

        this.wishlistSignal.set(mappati);
    }
}