import { Component, ChangeDetectorRef } from '@angular/core'; // 1. IMPORTATO ChangeDetectorRef
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';
import { ProductCard } from '../../components/product-card/product-card';
import { ThemeService } from "../../services/theme.service";
import { ModalLogin } from '../../components/modal-login/modal-login';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ProductCard, ModalLogin],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  mostraModale: boolean = false;
  
  // GESTIONE STATO E TIMER SNACKBAR
  mostraSnackbar: boolean = false;
  snackbarTimer: any;

  constructor(
      public langService: LangService,
      public themeService: ThemeService,
      public cartService: CartService,
      public wishlistService: WishlistService,
      private router: Router,
      private cdr: ChangeDetectorRef // 2. INIETTATO ChangeDetectorRef NEL COSTRUTTORE
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

    // Cancella il vecchio timer se l'utente clicca ripetutamente
    if (this.snackbarTimer) {
      clearTimeout(this.snackbarTimer);
    }

    // Mostra la snackbar e forza Angular ad accorgersi del cambio di stato
    this.mostraSnackbar = true;
    this.cdr.detectChanges(); 

    // Avvia il timer di 3 secondi per nasconderla
    this.snackbarTimer = setTimeout(() => {
      this.mostraSnackbar = false;
      this.cdr.detectChanges(); // 3. FORZA ANGULAR A AGGIORNARE LA PAGINA E NASCONDERE LA SNACKBAR
    }, 3000);
  }

  toggleWishlist(prodotto: any) {
    if (this.wishlistService.isInWishlist(prodotto.id)) {
      this.wishlistService.rimuovi(prodotto.id);
    } else {
      this.wishlistService.aggiungi(prodotto);
    }
  }
}
