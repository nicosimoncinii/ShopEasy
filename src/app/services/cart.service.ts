import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private elementiCarrello: any[] = [];

  getProdotti() {
    return this.elementiCarrello;
  }

  prodottiTutti = [
    { id: 1, nomeIt: 'iPhone 17 Pro',       nomeEn: 'iPhone 17 Pro',       prezzo: 1299 },
    { id: 2, nomeIt: 'Samsung Galaxy S25',   nomeEn: 'Samsung Galaxy S25',  prezzo: 1099 },
    { id: 3, nomeIt: 'MacBook Air M4',       nomeEn: 'MacBook Air M4',      prezzo: 1499 },
    { id: 4, nomeIt: 'iPad Pro 13"',         nomeEn: 'iPad Pro 13"',        prezzo: 1199 },
    { id: 5, nomeIt: 'AirPods Pro 3',        nomeEn: 'AirPods Pro 3',       prezzo: 299  },
    { id: 6, nomeIt: 'Samsung 4K OLED 55"',  nomeEn: 'Samsung 4K OLED 55"', prezzo: 899  },
    { id: 7, nomeIt: 'PlayStation 5 Slim',   nomeEn: 'PlayStation 5 Slim',  prezzo: 449  }
  ];
  aggiungiProdotto(prodotto: any) {
    const giaPresente = this.elementiCarrello.find(item => item.id === prodotto.id);
    if (giaPresente) {
      giaPresente.quantita += 1;
    } else {
      this.elementiCarrello.push({ ...prodotto, quantita: 1 });
    }
  }

  rimuoviProdotto(id: number) {
    this.elementiCarrello = this.elementiCarrello.filter(item => item.id !== id);
  }

  getTotale(): number {
    return this.elementiCarrello.reduce((tot, item) => tot + (item.prezzo * item.quantita), 0);
  }

  svuota() {
    this.elementiCarrello = [];
  }

  modificaQuantita(id: number, quantita: number) {
    const prodotto = this.elementiCarrello.find(item => item.id === id);
    if (prodotto) {
      if (quantita <= 0) {
        this.rimuoviProdotto(id); // se metti 0 lo elimina
      } else {
        prodotto.quantita = quantita;
      }
    }
  }
}