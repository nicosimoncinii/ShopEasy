import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.services';

@Component({
  selector: 'app-products',
  imports: [CommonModule],
  templateUrl: './products.html',
  styleUrl: './products.scss',
})
export class Products {
  prodotti = [
    { nome: 'Rolex', prezzo: 50 },
    { nome: 'Patek Philippe', prezzo: 80 },
    { nome: 'Hamilton', prezzo: 30 },
  ];

  constructor(private cartService: CartService) {}

  aggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungi(prodotto);
    console.log('Carrello:', this.cartService.scelti);
  }
}