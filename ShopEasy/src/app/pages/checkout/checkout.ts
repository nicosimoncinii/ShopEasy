import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.html',
  styleUrl: './checkout.scss'
})
export class Checkout {

  indirizzo: string = '';
  telefono: string = '';
  metodoPagamento: string = 'carta';

  numeroCarta: string = '';
  scadenza: string = '';
  cvv: string = '';
  emailPaypal: string = '';

  constructor(
      public langService: LangService,
      public cartService: CartService,
      private router: Router
  ) {}

  confermaOrdine() {
    if (!this.indirizzo || !this.telefono) {
      alert('Compila tutti i campi!');
      return;
    }

    let dettagliPagamento: any = {};
    if (this.metodoPagamento === 'carta') {
      dettagliPagamento = {
        numeroCarta: this.numeroCarta,
        scadenza: this.scadenza,
        cvv: this.cvv
      };
    } else if (this.metodoPagamento === 'paypal') {
      dettagliPagamento = {
        emailPaypal: this.emailPaypal
      };
    } else {
      dettagliPagamento = {
        note: 'Pagamento in contanti alla consegna'
      };
    }

    console.log('Ordine generato:', {
      indirizzo: this.indirizzo,
      telefono: this.telefono,
      metodoPagamento: this.metodoPagamento,
      datiPagamento: dettagliPagamento,
      prodotti: this.cartService.getProdotti(),
      totale: this.cartService.getTotale()
    });

    this.cartService.svuota();
    this.router.navigate(['/']);
  }
}