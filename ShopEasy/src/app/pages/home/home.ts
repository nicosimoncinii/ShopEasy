import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';
import { ProductCard } from '../../components/product-card/product-card';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ProductCard],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {

  constructor(
      public langService: LangService,
      public cartService: CartService,
      public wishlistService: WishlistService,
      private router: Router
  ) {}

  get bannerUrl(): string {
    return this.langService.getLingua() === 'it'
        ? '/banner.png'
        : '/bannerIG.png';
  }

  get prodottiInEvidenza() {
    return this.cartService.prodottiTutti.slice(0, 4);
  }

  get prodottiPopolari() {
    return this.cartService.prodottiTutti.slice(4, 7);
  }

  nomeProdotto(prodotto: any): string {
    return this.langService.getLingua() === 'it' ? prodotto.nomeIt : prodotto.nomeEn;
  }

  vaiAiProdotti() {
    this.router.navigate(['/prodotti']);
  }

  aggiungiAlCarrello(prodotto: any) {
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