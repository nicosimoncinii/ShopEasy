import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
prodottiTutti = [
  { id: 1, nomeIt: 'iPhone 17 Pro',       nomeEn: 'iPhone 17 Pro',       prezzo: 1299, img: '/iphone17pro.png' },
  { id: 2, nomeIt: 'Samsung Galaxy S25',   nomeEn: 'Samsung Galaxy S25',  prezzo: 1099, img: '/SamsungGalaxyS25.png' },
  { id: 3, nomeIt: 'MacBook Air M4',       nomeEn: 'MacBook Air M4',      prezzo: 1499, img: '/MacBookAirM4.png' },
  { id: 4, nomeIt: 'iPad Pro 13"',         nomeEn: 'iPad Pro 13"',        prezzo: 1199, img: '/iPadPro13.png' },
  { id: 5, nomeIt: 'AirPods Pro 3',        nomeEn: 'AirPods Pro 3',       prezzo: 299,  img: '/AirPodsPro3.png' },
  { id: 6, nomeIt: 'Samsung 4K OLED 55"',  nomeEn: 'Samsung 4K OLED 55"', prezzo: 899,  img: '/Samsung4KOLED55.webp' },
  { id: 7, nomeIt: 'PlayStation 5 Slim',   nomeEn: 'PlayStation 5 Slim',  prezzo: 449,  img: '/PlayStation5Slim.png' }
];

  constructor(
      public langService: LangService,
      private cartService: CartService
  ) {}

  ngOnInit() {}

  // usa direttamente la lingua dal servizio
  nomeProdotto(prodotto: any): string {
    return this.langService.getLingua() === 'it' ? prodotto.nomeIt : prodotto.nomeEn;
  }

  onAggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungiProdotto(prodotto);
  }
}