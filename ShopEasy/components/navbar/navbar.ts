import { Component } from '@angular/core';
import { RouterModule, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SearchService } from '../../services/search.service';
import { LangService } from '../../services/lang.service';
import { ThemeService } from '../../services/theme.service';

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
      public themeService: ThemeService
  ) {}

  onSearch(event: any) {
    this.searchService.cerca(event.target.value);
  }

  toggleDropdown() {
    this.dropdownAperto = !this.dropdownAperto;
  }

  cambiaLingua(lingua: string) {
    this.langService.caricaLingua(lingua); // ← era cambia(), ora è caricaLingua()
    this.dropdownAperto = false;
  }

  cambiaTema() {
    this.themeService.toggleTema();
  }
}