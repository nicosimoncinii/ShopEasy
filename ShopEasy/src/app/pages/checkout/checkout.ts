import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService, CartItem } from '../../services/cart.service';
import { LanguageService } from '../../services/language.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './checkout.html',
  styleUrl: './checkout.scss'
})
export class Checkout implements OnInit, OnDestroy {
  private cartSub!: Subscription;
  private langSub!: Subscription;

  items: CartItem[] = [];
  traduzioni: any = {};
  ordineConfermato = false;

  constructor(
    public cartService: CartService,
    public langService: LanguageService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cartSub = this.cartService.items$.subscribe(items => {
      this.items = items;
      this.cdr.detectChanges();
    });
    this.langSub = this.langService.lingua$.subscribe(lingua => {
      this.aggiornaTraduzione(lingua);
      this.cdr.detectChanges();
    });
  }

  aggiornaTraduzione(lingua: string) {
    this.traduzioni = {
      checkoutTitolo: this.langService.traduci('checkoutTitolo', lingua),
      checkoutSub: this.langService.traduci('checkoutSub', lingua),
      totale: this.langService.traduci('totale', lingua),
      confermaPagamento: this.langService.traduci('confermaPagamento', lingua),
      ordineConfermatoMsg: this.langService.traduci('ordineConfermato', lingua),
      continuaShopping: this.langService.traduci('continuaShopping', lingua),
      carrelloVuoto: this.langService.traduci('carrelloVuoto', lingua),
    };
  }

  get totale(): number {
    return this.cartService.totalePrezzo;
  }

  confermaOrdine() {
    // TODO: collegare a POST /orders quando integri il backend
    this.ordineConfermato = true;
    this.cartService.svuota();
  }

  ngOnDestroy() {
    if (this.cartSub) this.cartSub.unsubscribe();
    if (this.langSub) this.langSub.unsubscribe();
  }
}