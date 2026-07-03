import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  elementiCarrello = signal<any[]>([]);
  numeroProdotti = signal(0);
  
  // L'array mock 'prodottiTutti' è stato rimosso.

  getProdotti() {
    return this.elementiCarrello();
  }

  getTotale(): number {
    return this.elementiCarrello().reduce((tot, item) => tot + (item.price * item.quantita), 0);
  }

aggiungiProdotto(prodotto: any) {
  const lista = this.elementiCarrello();
  const giaPresente = lista.find(item => item.id === prodotto.id);

  // Blocco se il prodotto è esaurito
  if (prodotto.quantita === 0 || prodotto.quantita === undefined) {
    console.warn('Prodotto esaurito, impossibile aggiungere al carrello');
    return;
  }

  if (giaPresente) {
    if (giaPresente.quantita < giaPresente.stockDisponibile) {
      giaPresente.quantita += 1;
    }
  } else {
    lista.push({
      ...prodotto,
      stockDisponibile: prodotto.quantita, // stock originale del prodotto
      quantita: 1                          // quantita nel carrello parte da 1
    });
  }

  this.elementiCarrello.set([...lista]);
  this.aggiornaNumero();
}

  rimuoviProdotto(id: number) {
    const lista = this.elementiCarrello().filter(item => item.id !== id);
    this.elementiCarrello.set(lista);
    this.aggiornaNumero();
  }

  modificaQuantita(id: number, quantita: number) {
    const lista = this.elementiCarrello();
    const prodotto = lista.find(item => item.id === id);

    if (prodotto) {
      if (quantita <= 0) {
        this.rimuoviProdotto(id);
        return;
      }
      if (quantita > prodotto.stockDisponibile) {
        quantita = prodotto.stockDisponibile; // non superare lo stock
      }
      prodotto.quantita = quantita;
    }

    this.elementiCarrello.set([...lista]);
    this.aggiornaNumero();
  }

  aggiornaNumero() {
    const totale = this.elementiCarrello().reduce((tot, item) => tot + item.quantita, 0);
    this.numeroProdotti.set(totale);
  }

  svuota() {
    this.elementiCarrello.set([]);
    this.numeroProdotti.set(0);
  }
}