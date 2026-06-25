import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LangService } from '../../src/app/services/lang.service';
import { CartService } from '../../src/app/services/cart.service';
import { SearchService } from '../../src/app/services/search.service';
import { ProductService } from '../../src/app/services/product.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products implements OnInit, OnDestroy {
  private searchSub!: Subscription;

  searchQuery = '';
  prodottiTutti: any[] = [];
  prodottiVisualizzati: any[] = [];
  caricamento = true;
  errore = '';

  constructor(
    public langService: LangService,
    private cartService: CartService,
    private searchService: SearchService,
    private productService: ProductService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    // Carica prodotti dal backend
    this.productService.getProdotti().subscribe({
      next: (dati) => {
        this.prodottiTutti = dati;
        this.aggiornaProdotti();
        this.caricamento = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Errore caricamento prodotti:', err);
        this.errore = 'Errore nel caricamento dei prodotti';
        this.caricamento = false;
        this.cdr.detectChanges();
      }
    });

    this.searchSub = this.searchService.searchText$.subscribe(testo => {
      this.searchQuery = testo;
      this.aggiornaProdotti();
      this.cdr.detectChanges();
    });
  }

  aggiornaProdotti() {
    const query = this.searchQuery.toLowerCase().trim();
    const lingua = this.langService.getLingua();
    this.prodottiVisualizzati = this.prodottiTutti
      .map(p => ({
        ...p,
        nome: lingua === 'it' ? (p.nomeIt || p.nome) : (p.nomeEn || p.nome)
      }))
      .filter(p => !query || p.nome.toLowerCase().includes(query));
  }

  onAggiungiAlCarrello(prodotto: any) {
    this.cartService.aggiungiProdotto(prodotto);
  }

  ngOnDestroy() {
    if (this.searchSub) this.searchSub.unsubscribe();
  }
}