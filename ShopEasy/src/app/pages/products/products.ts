import { Component } from '@angular/core';
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
export class Products {

  get prodottiTutti() {
    return this.cartService.prodottiTutti;
  }

  constructor(
      public langService: LangService,
      public cartService: CartService
  ) {}

  nomeProdotto(prodotto: any): string {
    return this.langService.getLingua() === 'it' ? prodotto.nomeIt : prodotto.nomeEn;
  }

  onAggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungiProdotto(prodotto);
  }
}