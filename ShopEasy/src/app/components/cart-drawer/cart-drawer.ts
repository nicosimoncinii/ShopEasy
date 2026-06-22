import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CartService, CartItem } from '../../services/cart.service';
import { LanguageService } from '../../services/language.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-cart-drawer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart-drawer.html',
  styleUrl: './cart-drawer.scss'
})
export class CartDrawer implements OnInit, OnDestroy {
  private cartSub!: Subscription;
  private drawerSub!: Subscription;
  private langSub!: Subscription;

  items: CartItem[] = [];
  aperto = false;
  traduzioni: any = {};

  constructor(
    public cartService: CartService,
    public langService: LanguageService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cartSub = this.cartService.items$.subscribe(items => {
      this.items = items;
      this.cdr.detectChanges();
    });
    this.drawerSub = this.cartService.drawerAperto$.subscribe(aperto => {
      this.aperto = aperto;
      this.cdr.detectChanges();
    });
    this.langSub = this.langService.lingua$.subscribe(lingua => {
      this.aggiornaTraduzione(lingua);
      this.cdr.detectChanges();
    });
  }

  aggiornaTraduzione(lingua: string) {
    this.traduzioni = {
      carrelloTitolo: this.langService.traduci('carrelloTitolo', lingua),
      carrelloVuoto: this.langService.traduci('carrelloVuoto', lingua),
      totale: this.langService.traduci('totale', lingua),
      procedi: this.langService.traduci('procedi', lingua),
      continuaShopping: this.langService.traduci('continuaShopping', lingua),
    };
  }

  get totale(): number {
    return this.cartService.totalePrezzo;
  }

  chiudi() {
    this.cartService.chiudiDrawer();
  }

  vaiAlCheckout() {
    this.chiudi();
    this.router.navigate(['/checkout']);
  }

  vaiAiProdotti() {
    this.chiudi();
    this.router.navigate(['/prodotti']);
  }

  ngOnDestroy() {
    if (this.cartSub) this.cartSub.unsubscribe();
    if (this.drawerSub) this.drawerSub.unsubscribe();
    if (this.langSub) this.langSub.unsubscribe();
  }
}