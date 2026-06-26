import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  elementiCarrello = signal<any[]>([]);
  numeroProdotti = signal(0);

  prodottiTutti = [
    { id: 1, nomeIt: 'iPhone 17 Pro', nomeEn: 'iPhone 17 Pro', prezzo: 1299, img: '/iphone17pro.png' },
    { id: 2, nomeIt: 'Samsung Galaxy S25', nomeEn: 'Samsung Galaxy S25', prezzo: 1099, img: '/SamsungGalaxyS25.png' },
    { id: 3, nomeIt: 'MacBook Air M4', nomeEn: 'MacBook Air M4', prezzo: 1499, img: '/MacBookAirM4.png' },
    { id: 4, nomeIt: 'iPad Pro 13"', nomeEn: 'iPad Pro 13"', prezzo: 1199, img: '/iPadPro13.png' },
    { id: 5, nomeIt: 'AirPods Pro 3', nomeEn: 'AirPods Pro 3', prezzo: 299, img: '/AirPodsPro3.png' },
    { id: 6, nomeIt: 'Samsung 4K OLED 55"', nomeEn: 'Samsung 4K OLED 55"', prezzo: 899, img: '/Samsung4KOLED55.webp' },
    { id: 7, nomeIt: 'PlayStation 5 Slim', nomeEn: 'PlayStation 5 Slim', prezzo: 449, img: '/PlayStation5Slim.png' }
  ];

  // metodi compatibili con il vecchio codice
  getProdotti() {
    return this.elementiCarrello();
  }

  getTotale(): number {
    return this.elementiCarrello().reduce((tot, item) => tot + (item.prezzo * item.quantita), 0);
  }

  aggiungiProdotto(prodotto: any) {
    const lista = this.elementiCarrello();
    const giaPresente = lista.find(item => item.id === prodotto.id);

    if (giaPresente) {
      giaPresente.quantita += 1;
    } else {
      lista.push({ ...prodotto, quantita: 1 });
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