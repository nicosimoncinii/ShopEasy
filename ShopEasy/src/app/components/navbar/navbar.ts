import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'; // <-- NUOVO: Fornisce la UpperCasePipe e altre utility
import { SearchService } from '../../services/search.service';
import { LanguageService } from '../../services/language.service';
import { ThemeService } from '../../services/theme.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule, CommonModule], // <-- MODIFICATO: Aggiunto CommonModule qui dentro
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class Navbar {
  linguaAttuale = 'it';
  dropdownAperto = false;

  constructor(
    private searchService: SearchService,
    public langService: LanguageService,
    public themeService: ThemeService
  ) {}

  onSearch(event: any) {
    this.searchService.cerca(event.target.value);
  }

  toggleDropdown() {
    this.dropdownAperto = !this.dropdownAperto;
  }

  cambiaLingua(lingua: string) {
    this.linguaAttuale = lingua;
    this.langService.cambia(lingua);
    this.dropdownAperto = false;
  }

  cambiaTema() {
    this.themeService.toggleTema();
  }
}
