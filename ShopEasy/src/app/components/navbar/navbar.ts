import { Component } from '@angular/core';
import { RouterModule, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NgIconComponent } from '@ng-icons/core';
import { SearchService } from '../../services/search.service';
import { LangService } from '../../services/lang.service';
import { ThemeService } from '../../services/theme.service';
import { CartService } from '../../services/cart.service';


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {

  dropdownAperto = false;

  constructor(
      private searchService: SearchService,
      public langService: LangService,
      public themeService: ThemeService,
      public cartService: CartService
  ) {}

  get numeroProdottiCarrello() {
    return this.cartService.numeroProdotti();
  }


  onSearch(event: any) {
    this.searchService.cerca(event.target.value);
  }

  toggleDropdown() {
    this.dropdownAperto = !this.dropdownAperto;
  }

  cambiaLingua(lingua: string) {
    this.langService.caricaLingua(lingua);
    this.dropdownAperto = false;
  }

  cambiaTema() {
    this.themeService.toggleTema();
  }
}