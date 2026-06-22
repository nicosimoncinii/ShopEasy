import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LanguageService } from '../../services/language.service';
import { SearchService } from '../../services/search.service';
import { CartService } from '../../services/cart.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products implements OnInit, OnDestroy {
  private langSub!: Subscription;
  private searchSub!: Subscription;

  linguaCorrente = 'it';
  searchQuery = '';

  prodottiTutti = [
    { id: 1, nomeIt: 'iPhone 17 Pro',       nomeEn: 'iPhone 17 Pro',       prezzo: 1299 },
    { id: 2, nomeIt: 'Samsung Galaxy S25',   nomeEn: 'Samsung Galaxy S25',  prezzo: 1099 },
    { id: 3, nomeIt: 'MacBook Air M4',       nomeEn: 'MacBook Air M4',      prezzo: 1499 },
    { id: 4, nomeIt: 'iPad Pro 13"',         nomeEn: 'iPad Pro 13"',        prezzo: 1199 },
    { id: 5, nomeIt: 'AirPods Pro 3',        nomeEn: 'AirPods Pro 3',       prezzo: 299  },
    { id: 6, nomeIt: 'Samsung 4K OLED 55"', nomeEn: 'Samsung 4K OLED 55"', prezzo: 899  },
    { id: 7, nomeIt: 'PlayStation 5 Slim',   nomeEn: 'PlayStation 5 Slim',  prezzo: 449  }
  ];

  prodottiVisualizzati: any[] = [];
  traduzioni: any = {};

  constructor(
    public langService: LanguageService,
    private searchService: SearchService,
    private cartService: CartService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.langSub = this.langService.lingua$.subscribe(lingua => {
      this.linguaCorrente = lingua;
      this.aggiornaTraduzione(lingua);
      this.aggiornaProdotti();
      this.cdr.detectChanges();
    });

    this.searchSub = this.searchService.searchText$.subscribe(testo => {
      this.searchQuery = testo;
      this.aggiornaProdotti();
      this.cdr.detectChanges();
    });
  }

  aggiornaTraduzione(lingua: string) {
    this.traduzioni = {
      prodottiTitolo: this.langService.traduci('prodottiTitolo', lingua),
      aggiungi:       this.langService.traduci('aggiungi', lingua),
      nessunProdotto: this.langService.traduci('nessunProdotto', lingua)
    };
  }

  aggiornaProdotti() {
    const query = this.searchQuery.toLowerCase().trim();
    this.prodottiVisualizzati = this.prodottiTutti
      .map(p => ({
        ...p,
        nome: this.linguaCorrente === 'it' ? p.nomeIt : p.nomeEn
      }))
      .filter(p => !query || p.nome.toLowerCase().includes(query));
  }

  aggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungi({
      id: prodotto.id,
      nome: prodotto.nome,
      prezzo: prodotto.prezzo
    });
  }

  ngOnDestroy() {
    if (this.langSub) this.langSub.unsubscribe();
    if (this.searchSub) this.searchSub.unsubscribe();
  }
}