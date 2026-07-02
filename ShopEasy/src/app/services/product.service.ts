import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Product } from '../models/product'; // Assicurati che il percorso sia corretto

@Injectable({ providedIn: 'root' })
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/products';

  // Usiamo un signal per mantenere lo stato globale dei prodotti
  prodottiTutti = signal<Product[]>([]);

  constructor(private http: HttpClient) {}

  caricaProdotti(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      tap(prodottiApi => {
        // Mappiamo i campi del DB sui campi attesi dal frontend
        const prodottiMappati = prodottiApi.map(p => ({
          id: p.id,
          name: p.nome,
          nomeIt: p.nome,
          nomeEn: p.nome, // Da aggiornare in futuro se il DB gestirà le lingue
          price: p.prezzo,
          imageUrl: p.immagine,
          quantitaMagazzino: p.quantita,
          categoriaId: p.categoriaId,
          categoriaNome: p.categoriaNome,
          // Valori di default per non rompere la UI se assenti nel DB
          listPrice: p.prezzo, 
          rating: 0,
          reviewsCount: 0,
          colors: []
        }));
        
        this.prodottiTutti.set(prodottiMappati);
      })
    );
  }

  getProdottoById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
}