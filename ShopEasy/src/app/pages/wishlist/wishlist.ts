import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { WishlistService } from '../../services/wishlist.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './wishlist.html',
  styleUrl: './wishlist.scss'
})
export class Wishlist {

  constructor(
      public langService: LangService,
      public wishlistService: WishlistService,
      public cartService: CartService,
      private router: Router
  ) {}

  rimuoviDaWishlist(id: number) {
    this.wishlistService.rimuovi(id);
  }

  aggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungiProdotto(prodotto);
    this.wishlistService.rimuovi(prodotto.id);
  }

  tornaAiProdotti() {
    this.router.navigate(['/prodotti']);
  }
}