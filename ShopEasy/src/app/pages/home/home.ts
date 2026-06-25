import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {

  constructor(
      public langService: LangService,
      public cartService: CartService,
      private router: Router
  ) {}

  get prodottiInEvidenza() {
    return this.cartService.prodottiTutti.slice(0, 4);
  }

  get prodottiPopolari() {
    return this.cartService.prodottiTutti.slice(4, 7);
  }
  nomeProdotto(prodotto: any): string {
    return this.langService.getLingua() === 'it'
        ? prodotto.nomeIt
        : prodotto.nomeEn;
  }

  vaiAiProdotti() {
    this.router.navigate(['/prodotti']);
  }

  aggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungiProdotto(prodotto);
  }
}