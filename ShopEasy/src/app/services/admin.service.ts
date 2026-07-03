import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {

    private baseUrl = 'http://localhost:8080/api';

    constructor(private http: HttpClient) {}

    private getHeaders(): { headers: HttpHeaders } {
        const token = localStorage.getItem('token');
        return {
            headers: new HttpHeaders({
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            })
        };
    }

    // STATISTICHE
    getStatistiche(): Observable<any> {
        return this.http.get(`${this.baseUrl}/dashboard`, this.getHeaders());
    }

    // ORDINI
    getTuttiOrdini(): Observable<any[]> {
        return this.http.get<any[]>(`${this.baseUrl}/ordini`, this.getHeaders());
    }

    aggiornaOrdine(id: number, ordine: any): Observable<any> {
        return this.http.put(`${this.baseUrl}/ordini/${id}`, ordine, this.getHeaders());
    }

    cancellaOrdine(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/ordini/${id}`, this.getHeaders());
    }

    // PRODOTTI
    getTuttiProdotti(): Observable<any[]> {
        return this.http.get<any[]>(`${this.baseUrl}/products`, this.getHeaders());
    }

    creaProdotto(prodotto: any): Observable<any> {
        return this.http.post(`${this.baseUrl}/products`, prodotto, this.getHeaders());
    }

    aggiornaProdotto(id: number, prodotto: any): Observable<any> {
        return this.http.put(`${this.baseUrl}/products/${id}`, prodotto, this.getHeaders());
    }

    cancellaProdotto(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/products/${id}`, this.getHeaders());
    }

    // UTENTI
    getTuttiUtenti(): Observable<any[]> {
        return this.http.get<any[]>(`${this.baseUrl}/users`, this.getHeaders());
    }

    cancellaUtente(id: number): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/users/${id}`, this.getHeaders());
    }
}