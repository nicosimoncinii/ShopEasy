import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LanguageService } from '../../services/language.service';
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
  linguaCorrente = 'it';

  prodottiTutti = [
    { id: 1, nomeIt: 'iPhone 17 Pro', nomeEn: 'iPhone 17 Pro', prezzo: '1299€' },
    { id: 2, nomeIt: 'Samsung Galaxy S25', nomeEn: 'Samsung Galaxy S25', prezzo: '1099€' },
    { id: 3, nomeIt: 'MacBook Air M4', nomeEn: 'MacBook Air M4', prezzo: '1499€' },
    { id: 4, nomeIt: 'iPad Pro 13"', nomeEn: 'iPad Pro 13"', prezzo: '1199€' },
    { id: 5, nomeIt: 'AirPods Pro 3', nomeEn: 'AirPods Pro 3', prezzo: '299€' },
    { id: 6, nomeIt: 'Samsung 4K OLED 55"', nomeEn: 'Samsung 4K OLED 55"', prezzo: '899€' },
    { id: 7, nomeIt: 'PlayStation 5 Slim', nomeEn: 'PlayStation 5 Slim', prezzo: '449€' }
  ];

  // Deve essere presente ed essere public/propria della classe
  prodottiVisualizzati: any[] = [];

  // Il costruttore DEVE avere "public langService" per essere visto nell'HTML
  constructor(public langService: LanguageService) {}

  ngOnInit() {
    this.langSub = this.langService.lingua$.subscribe(lingua => {
      this.linguaCorrente = lingua;
      this.aggiornaProdotti();
    });
  }

  aggiornaProdotti() {
    this.prodottiVisualizzati = this.prodottiTutti.map(p => ({
      ...p,
      nome: this.linguaCorrente === 'it' ? p.nomeIt : p.nomeEn
    }));
  }

  ngOnDestroy() {
    if (this.langSub) {
      this.langSub.unsubscribe();
    }
  }
}
