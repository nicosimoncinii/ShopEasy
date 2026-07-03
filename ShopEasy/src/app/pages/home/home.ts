import { Component, ChangeDetectorRef, computed, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';
import { SearchService } from '../../services/search.service';
import { ProductService } from '../../services/product.service'; // Aggiunto
import { ProductCard } from '../../components/product-card/product-card';
import { ThemeService } from '../../services/theme.service';
import { ModalLogin } from '../../components/modal-login/modal-login';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ProductCard, ModalLogin],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit { // Aggiunto OnInit
  mostraModale: boolean = false;
  mostraSnackbar: boolean = false;
  snackbarTimer: any;

  private testoCerca!: ReturnType<typeof toSignal<string>>;

  prodottiFiltrati = computed(() => {
    const testo = this.testoCerca?.() ?? '';
    // Leggiamo i prodotti dal ProductService invece che dal CartService
    const tutti = this.productService.prodottiTutti(); 
    
    if (!testo.trim()) return tutti.slice(0, 4);
    return tutti.filter(p =>
        p.name.toLowerCase().includes(testo.toLowerCase().trim())
    );
  });

  constructor(
      public langService: LangService,
      public themeService: ThemeService,
      public cartService: CartService,
      public wishlistService: WishlistService,
      private searchService: SearchService,
      private productService: ProductService, // Aggiunto
      private router: Router,
      private cdr: ChangeDetectorRef
  ) {
    this.testoCerca = toSignal(this.searchService.searchText$, { initialValue: '' });
  }

  ngOnInit() {
    // Se la lista è vuota, scateniamo la chiamata GET
    if (this.productService.prodottiTutti().length === 0) {
      this.productService.caricaProdotti().subscribe();
    }
  }

  get bannerUrl(): string {
    const lingua = this.langService.getLingua();
    const isDark = this.themeService.getIsDarkMode();
    if (isDark) {
      return lingua === 'it' ? '/darkbannerIT.png' : '/darkbannerIG.png';
    } else {
      return lingua === 'it' ? '/banner.png' : '/bannerIG.png';
    }
  }

  nomeProdotto(prodotto: any): string {
    return this.langService.getLingua() === 'it' ? prodotto.nomeIt : prodotto.nomeEn;
  }

  vaiAiProdotti() {
    this.router.navigate(['/prodotti']);
  }

  aggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungiProdotto(prodotto);
    if (this.snackbarTimer) clearTimeout(this.snackbarTimer);
    this.mostraSnackbar = true;
    this.cdr.detectChanges();
    this.snackbarTimer = setTimeout(() => {
      this.mostraSnackbar = false;
      this.cdr.detectChanges();
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