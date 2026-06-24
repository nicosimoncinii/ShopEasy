import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';



@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.html',
  styleUrl: './cart.scss'
})
export class Cart {

  constructor(
      private router: Router,
      public langService: LangService,
      public cartService: CartService,

  ) {}

  eliminaProdotto(id: number) {
    this.cartService.rimuoviProdotto(id);
  }

  modificaQuantita(id: number, quantita: number) {
    this.cartService.modificaQuantita(id, quantita);
  }

  get prodottiTutti() {
    return this.cartService.prodottiTutti;
  }
  tornaAlloShopping() {
    this.router.navigate(['/prodotti']);
  }

  nomeProdotto(prodotto: any): string {
    return this.langService.getLingua() === 'it' ? prodotto.nomeIt : prodotto.nomeEn;
  }


}