import { Injectable, signal } from '@angular/core';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  elementiCarrello = signal<any[]>([]);
  numeroProdotti = signal(0);
  
  prodottiTutti: Product[] = [
    { id: 1, name: 'iPhone 17 Pro', imageUrl: '/iphone17pro.png', price: 1299, listPrice: 1499, rating: 5, reviewsCount: 342, colors: ['#1a1a1a', '#f5f5f0'], nomeIt: 'iPhone 17 Pro', nomeEn: 'iPhone 17 Pro' },
    { id: 2, name: 'Samsung Galaxy S25', imageUrl: '/SamsungGalaxyS25.png', price: 1099, listPrice: 1299, rating: 4, reviewsCount: 210, colors: ['#3b3b3c', '#f5f5f5'], nomeIt: 'Samsung Galaxy S25', nomeEn: 'Samsung Galaxy S25' },
    { id: 3, name: 'MacBook Air M4', imageUrl: '/MacBookAirM4.png', price: 1499, listPrice: 1699, rating: 5, reviewsCount: 560, nomeIt: 'MacBook Air M4', nomeEn: 'MacBook Air M4' },
    { id: 4, name: 'iPad Pro 13"', imageUrl: '/iPadPro13.png', price: 1199, listPrice: 1399, rating: 4, reviewsCount: 180, nomeIt: 'iPad Pro 13"', nomeEn: 'iPad Pro 13"' },
    { id: 5, name: 'AirPods Pro 3', imageUrl: '/AirPodsPro3.png', price: 299, listPrice: 349, rating: 5, reviewsCount: 890, nomeIt: 'AirPods Pro 3', nomeEn: 'AirPods Pro 3' },
    { id: 6, name: 'Samsung 4K OLED 55"', imageUrl: '/Samsung4KOLED55.webp', price: 899, listPrice: 1099, rating: 4, reviewsCount: 120, nomeIt: 'Samsung 4K OLED 55"', nomeEn: 'Samsung 4K OLED 55"' },
    { id: 7, name: 'PlayStation 5 Slim', imageUrl: '/PlayStation5Slim.png', price: 449, listPrice: 549, rating: 5, reviewsCount: 1200, nomeIt: 'PlayStation 5 Slim', nomeEn: 'PlayStation 5 Slim' }
  ];

  getProdotti() {
    return this.elementiCarrello();
  }

  getTotale(): number {
    return this.elementiCarrello().reduce((tot, item) => tot + (item.price * item.quantita), 0);
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
