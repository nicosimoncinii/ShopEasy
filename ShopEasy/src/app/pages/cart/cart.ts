import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';
import { ModalLogin } from '../../components/modal-login/modal-login';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule, ModalLogin],
  templateUrl: './cart.html',
  styleUrl: './cart.scss'
})
export class Cart {

  mostraModale = false;

  constructor(
      private router: Router,
      public langService: LangService,
      public cartService: CartService,
      private authService: AuthService
  ) {}

  vaiAlPagamento() {
    if (!this.authService.isLoggato()) {
      this.mostraModale = true;
      return;
    }
    this.router.navigate(['/checkout']);
  }

  eliminaProdotto(id: number) {
    this.cartService.rimuoviProdotto(id);
  }

  modificaQuantita(id: number, quantita: number) {
    if (quantita > 0) {
      this.cartService.modificaQuantita(id, quantita);
    }
  }

  tornaAlloShopping() {
    this.router.navigate(['/']);
  }

  nomeProdotto(prodotto: any): string {
    return this.langService.getLingua() === 'it' ? prodotto.nomeIt : prodotto.nomeEn;
  }
}