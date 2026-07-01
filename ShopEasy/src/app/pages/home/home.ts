import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';
import { ProductCard } from '../../components/product-card/product-card';
import { ThemeService } from "../../services/theme.service";
// 1. IMPORTA IL MODALE
import { ModalLogin } from '../../components/modal-login/modal-login';

@Component({
  selector: 'app-home',
  standalone: true,
  // 2. AGGIUNGI ModalLogin NEGLI IMPORTS
  imports: [CommonModule, ProductCard, ModalLogin],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  // 3. DICHIARA LA VARIABILE PER CONTROLLARE IL MODALE
  mostraModale: boolean = false;

  constructor(
      public langService: LangService,
      public themeService: ThemeService,
      public cartService: CartService,
      public wishlistService: WishlistService,
      private router: Router
  ) {}

  get bannerUrl(): string {
    const lingua = this.langService.getLingua();
    const isDark = this.themeService.getIsDarkMode();

    if (isDark) {
      return lingua === 'it' ? '/darkbannerIT.png' : '/darkbannerIG.png';
    } else {
      return lingua === 'it' ? '/banner.png' : '/bannerIG.png';
    }
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