import { Component, HostListener, OnInit, OnDestroy } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SearchService } from '../../services/search.service';
import { LanguageService } from '../../services/language.service';
import { ThemeService } from '../../services/theme.service';
import { CartService } from '../../services/cart.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar implements OnInit, OnDestroy {
  private langSub!: Subscription;
  private cartSub!: Subscription;

  linguaAttuale = 'it';
  dropdownAperto = false;
  searchQuery = '';
  suggerimenti: any[] = [];
  searchFocused = false;
  traduzioni: any = {};
  cartQuantita = 0;

  prodotti = [
    { id: 1, nomeIt: 'iPhone 17 Pro',       nomeEn: 'iPhone 17 Pro'       },
    { id: 2, nomeIt: 'Samsung Galaxy S25',   nomeEn: 'Samsung Galaxy S25'  },
    { id: 3, nomeIt: 'MacBook Air M4',       nomeEn: 'MacBook Air M4'      },
    { id: 4, nomeIt: 'iPad Pro 13"',         nomeEn: 'iPad Pro 13"'        },
    { id: 5, nomeIt: 'AirPods Pro 3',        nomeEn: 'AirPods Pro 3'       },
    { id: 6, nomeIt: 'Samsung 4K OLED 55"', nomeEn: 'Samsung 4K OLED 55"' },
    { id: 7, nomeIt: 'PlayStation 5 Slim',   nomeEn: 'PlayStation 5 Slim'  }
  ];

  constructor(
    private searchService: SearchService,
    public langService: LanguageService,
    public themeService: ThemeService,
    public cartService: CartService
  ) {}

  ngOnInit() {
    this.langSub = this.langService.lingua$.subscribe(lingua => {
      this.linguaAttuale = lingua;
      this.aggiornaTraduzione(lingua);
    });
    this.cartSub = this.cartService.items$.subscribe(() => {
      this.cartQuantita = this.cartService.totaleQuantita;
    });
  }

  aggiornaTraduzione(lingua: string) {
    this.traduzioni = {
      home:     this.langService.traduci('home', lingua),
      prodotti: this.langService.traduci('prodotti', lingua),
      carrello: this.langService.traduci('carrello', lingua),
      cerca:    this.langService.traduci('cerca', lingua),
    };
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: any) {
    if (!event.target.closest('.search-bar')) {
      this.searchFocused = false;
      this.suggerimenti = [];
    }
    if (!event.target.closest('.language-selector')) {
      this.dropdownAperto = false;
    }
  }

  onSearch(event: any) {
    this.searchQuery = event.target.value;
    if (this.searchQuery.trim() === '') {
      this.searchService.reset();
      this.suggerimenti = [];
      return;
    }
    this.searchService.cerca(this.searchQuery);
    const query = this.searchQuery.toLowerCase().trim();
    this.suggerimenti = this.prodotti
      .map(p => ({ ...p, nome: this.linguaAttuale === 'it' ? p.nomeIt : p.nomeEn }))
      .filter(p => p.nome.toLowerCase().includes(query))
      .slice(0, 5);
  }

  selezionaSuggerimento(prodotto: any) {
    this.searchQuery = prodotto.nome;
    this.searchService.cerca(prodotto.nome);
    this.suggerimenti = [];
    this.searchFocused = false;
  }

  onFocus() { this.searchFocused = true; }

  onBlur() {
    setTimeout(() => {
      this.searchFocused = false;
      this.suggerimenti = [];
    }, 150);
  }

  toggleDropdown() { this.dropdownAperto = !this.dropdownAperto; }

  cambiaLingua(lingua: string) {
    this.linguaAttuale = lingua;
    this.langService.cambia(lingua);
    this.dropdownAperto = false;
  }

  cambiaTema() { this.themeService.toggleTema(); }

  ngOnDestroy() {
    if (this.langSub) this.langSub.unsubscribe();
    if (this.cartSub) this.cartSub.unsubscribe();
  }
}