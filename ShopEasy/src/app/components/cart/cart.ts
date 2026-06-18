import { Component } from '@angular/core';

@Component({
  selector: 'app-cart',
  standalone: true,
  templateUrl: './cart.html', // Controlla se si chiama cart.html o cart.component.html
  styleUrl: './cart.scss'     // Controlla se si chiama cart.scss o cart.component.scss
})
export class CartComponent {
  // Dati del carrello per Shop Easy
  carrello = [
    { prodotto: { nome: 'Smartphone Tech X', prezzo: 699 }, quantita: 1 },
    { prodotto: { nome: 'Cuffie Bluetooth ANC', prezzo: 149 }, quantita: 2 }
  ];

  rimuoviDalCarrello(item: any) {
    this.carrello = this.carrello.filter(c => c !== item);
  }
}
