import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgIconComponent } from '@ng-icons/core';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, NgIconComponent],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products {

  get prodottiTutti() {
    return this.cartService.prodottiTutti;
  }

  constructor(
      public langService: LangService,
      public cartService: CartService,
      public wishlistService: WishlistService
  ) {}

  nomeProdotto(prodotto: any): string {
    return this.langService.getLingua() === 'it' ? prodotto.nomeIt : prodotto.nomeEn;
  }

  onAggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungiProdotto(prodotto);
  }

  toggleWishlist(prodotto: any) {
    if (this.wishlistService.isInWishlist(prodotto.id)) {
      this.wishlistService.rimuovi(prodotto.id);
    } else {
      this.wishlistService.aggiungi(prodotto);
    }
  }
}