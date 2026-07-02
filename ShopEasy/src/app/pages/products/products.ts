import { Component, signal, effect, ChangeDetectorRef, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductList } from '../../components/product-list/product-list';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { SearchService } from '../../services/search.service';
import { ModalLogin } from '../../components/modal-login/modal-login';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, ProductList, ModalLogin],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products {
  mostraModal = signal<boolean>(false);
  mostraSnackbar: boolean = false;
  snackbarTimer: any;
  private conteggioPrecedente = 0;

  private testoCerca!: ReturnType<typeof toSignal<string>>;

  prodottiFiltrati = computed(() => {
    const testo = this.testoCerca?.() ?? '';
    if (!testo.trim()) return this.cartService.prodottiTutti;
    return this.cartService.prodottiTutti.filter(p =>
        p.name.toLowerCase().includes(testo.toLowerCase().trim())
    );
  });

  constructor(
      public langService: LangService,
      private cartService: CartService,
      private searchService: SearchService,
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