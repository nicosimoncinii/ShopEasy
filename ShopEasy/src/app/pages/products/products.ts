import { Component, signal, effect, ChangeDetectorRef, computed, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductList } from '../../components/product-list/product-list';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { SearchService } from '../../services/search.service';
import { ProductService } from '../../services/product.service'; // Aggiunto
import { ModalLogin } from '../../components/modal-login/modal-login';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, ProductList, ModalLogin],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products implements OnInit { // Aggiunto OnInit
  mostraModal = signal<boolean>(false);
  mostraSnackbar: boolean = false;
  snackbarTimer: any;
  private conteggioPrecedente = 0;

  private testoCerca!: ReturnType<typeof toSignal<string>>;

  prodottiFiltrati = computed(() => {
    const testo = this.testoCerca?.() ?? '';
    // Leggiamo dal ProductService
    const tutti = this.productService.prodottiTutti();
    
    if (!testo.trim()) return tutti;
    return tutti.filter(p =>
        p.name.toLowerCase().includes(testo.toLowerCase().trim())
    );
  });

  constructor(
      public langService: LangService,
      private cartService: CartService,
      private searchService: SearchService,
      private productService: ProductService, // Aggiunto
      private cdr: ChangeDetectorRef
  ) {
    this.testoCerca = toSignal(this.searchService.searchText$, { initialValue: '' });
    this.conteggioPrecedente = this.cartService.numeroProdotti();

    effect(() => {
      const conteggioAttuale = this.cartService.numeroProdotti();
      if (conteggioAttuale > this.conteggioPrecedente) {
        this.attivaSnackbar();
      }
      this.conteggioPrecedente = conteggioAttuale;
    });
  }

  ngOnInit() {
    // Chiamata GET in caso l'utente atterri direttamente qui
    if (this.productService.prodottiTutti().length === 0) {
      this.productService.caricaProdotti().subscribe();
    }
  }

  attivaSnackbar() {
    if (this.snackbarTimer) clearTimeout(this.snackbarTimer);
    this.mostraSnackbar = true;
    this.cdr.detectChanges();
    this.snackbarTimer = setTimeout(() => {
      this.mostraSnackbar = false;
      this.cdr.detectChanges();
    }, 3000);
  }
}