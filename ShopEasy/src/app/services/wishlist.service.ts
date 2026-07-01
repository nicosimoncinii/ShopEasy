import { Injectable, signal, computed } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class WishlistService {

    private wishlistSignal = signal<any[]>([]);

    // URL Assoluto verso Spring Boot senza passare dal proxy
    private readonly API_BASE_URL = 'http://localhost:8080/api/wishlist/me';

    public readonly prodotti = computed(() => this.wishlistSignal());

    constructor(private http: HttpClient) {
        this.caricaDaBackend();
    }

    // Funzione di utilità privata per generare l'header con il Token JWT
    private getAuthHeaders(): { headers: HttpHeaders } {
        const token = localStorage.getItem('token'); // Cambia 'token' con la tua chiave se diversa (es. 'jwt')
        return {
            headers: new HttpHeaders({
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            })
        };
    }

    private caricaDaBackend() {
        this.http.get<any>(this.API_BASE_URL, this.getAuthHeaders()).subscribe({
            next: (res) => {
                // Estrae l'array dal DTO (res.prodotti)
                const listaProdotti = res && res.prodotti ? res.prodotti : [];
                this.aggiornaLista(listaProdotti);
            },
            error: (err) => console.error('Errore caricamento wishlist dal DB', err)
        });
    }

    aggiungi(prodotto: any) {
        const giaPresente = this.wishlistSignal().some(p => p.id === prodotto.id);
        if (!giaPresente) {
            const url = `${this.API_BASE_URL}/prodotti/${prodotto.id}`;
            this.http.post<any>(url, {}, this.getAuthHeaders()).subscribe({
                next: (res) => {
                    // Aggiorna lo stato con la nuova lista tornata dal DTO del backend
                    const listaProdotti = res && res.prodotti ? res.prodotti : [];
                    this.aggiornaLista(listaProdotti);
                },
                error: (err) => console.error('Errore aggiunta prodotto alla wishlist', err)
            });
        }
    }

    rimuovi(id: number) {
        const url = `${this.API_BASE_URL}/prodotti/${id}`;
        this.http.delete<any>(url, this.getAuthHeaders()).subscribe({
            next: (res) => {
                const listaProdotti = res && res.prodotti ? res.prodotti : [];
                this.aggiornaLista(listaProdotti);
            },
            error: (err) => console.error('Errore rimozione prodotto dalla wishlist', err)
        });
    }

    isInWishlist(id: number): boolean {
        return this.wishlistSignal().some(p => p.id === id);
    }

    private aggiornaLista(prodottiBackend: any[]) {
        const mappati = prodottiBackend.map(p => ({
            ...p,
            name: p.nome || p.name,
            price: p.prezzo || p.price,
            imageUrl: p.immagine || p.imageUrl
        }));
        this.wishlistSignal.set(mappati);
    }

    svuota() {
        this.wishlistSignal.set([]);
    }

    ricarica() {
        this.caricaDaBackend();
    }
}