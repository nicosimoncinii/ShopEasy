import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  scelti: any[] = [];

  aggiungi(prodotto: any) {
    this.scelti.push(prodotto);
  }

  rimuovi(index: number) {
    this.scelti.splice(index, 1);
  }
}