import { Component, signal, effect, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductList } from '../../components/product-list/product-list';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { ModalLogin } from '../../components/modal-login/modal-login';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, ProductList, ModalLogin],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products {
  mostraModal = signal<boolean>(false);

  // Gestione snackbar classica della pagina
  mostraSnackbar: boolean = false;
  snackbarTimer: any;
  
  // Memorizza il numero precedente di prodotti per evitare che appaia all'apertura della pagina
  private conteggioPrecedente = 0;

  constructor(
    public langService: LangService,
    private cartService: CartService,
    private cdr: ChangeDetectorRef
  ) {
    // Salviamo il numero iniziale di prodotti presenti nel carrello
    this.conteggioPrecedente = this.cartService.numeroProdotti();

    // L'EFFECT ASCOLTA IN AUTOMATICO QUALSIASI CAMBIAMENTO DEL CARRELLO
    effect(() => {
      const conteggioAttuale = this.cartService.numeroProdotti();

      // Se il numero attuale è maggiore di quello precedente, l'utente ha aggiunto un prodotto!
      if (conteggioAttuale > this.conteggioPrecedente) {
        this.attivaSnackbar();
      }
      
      // Tiene aggiornato il conteggio per i click successivi
      this.conteggioPrecedente = conteggioAttuale;
    });
  }

  attivaSnackbar() {
    if (this.snackbarTimer) {
      clearTimeout(this.snackbarTimer);
    }

    this.mostraSnackbar = true;
    this.cdr.detectChanges(); // Forza l'aggiornamento visivo immediato

    this.snackbarTimer = setTimeout(() => {
      this.mostraSnackbar = false;
      this.cdr.detectChanges(); // Nasconde la snackbar dopo 3 secondi
    }, 3000);
  }
}
