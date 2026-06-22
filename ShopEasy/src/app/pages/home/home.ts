import {
  Component,
  OnInit,
  OnDestroy,
  ChangeDetectorRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { LanguageService } from '../../services/language.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit, OnDestroy {

  private langSub!: Subscription;
  private timer: any;

  linguaAttuale = 'it';

  traduzioni: any = {};

  banners = [
    { src: '/Banner1.png', alt: 'Promozione 1' },
    { src: '/Banner2.png', alt: 'Promozione 2' },
    { src: '/Banner3.png', alt: 'Promozione 3' }
  ];

  prodottiEvidenza = [
    { id: 1, nome: 'iPhone 17 Pro', prezzo: 1299, badge: 'Novità' },
    { id: 2, nome: 'MacBook Air M4', prezzo: 1499, badge: 'Top' },
    { id: 3, nome: 'PlayStation 5 Slim', prezzo: 449, badge: 'Best Seller' },
    { id: 4, nome: 'AirPods Pro 3', prezzo: 299, badge: null }
  ];

  offerte = [
    {
      id: 5,
      nome: 'Samsung Galaxy S25',
      prezzoOriginale: 1099,
      prezzoScontato: 899,
      sconto: 18
    },
    {
      id: 6,
      nome: 'iPad Pro 13"',
      prezzoOriginale: 1199,
      prezzoScontato: 999,
      sconto: 17
    },
    {
      id: 7,
      nome: 'Samsung 4K OLED 55"',
      prezzoOriginale: 899,
      prezzoScontato: 699,
      sconto: 22
    }
  ];

  currentIndex = 0;
  featuredIndex = 0;
  isAnimating = false;

  constructor(
    private langService: LanguageService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {

    this.langSub = this.langService.lingua$.subscribe(lingua => {

      this.linguaAttuale = lingua;

      this.traduzioni = {
        prodottiEvidenza:
          this.langService.traduci('prodottiEvidenza', lingua),

        vediTutti:
          this.langService.traduci('vediTutti', lingua),

        scopri:
          this.langService.traduci('scopri', lingua),

        offerteAttive:
          this.langService.traduci('offerteAttive', lingua),

        vediTutte:
          this.langService.traduci('vediTutte', lingua),

        prezziRidotti:
          this.langService.traduci('prezziRidotti', lingua),

        acquista:
          this.langService.traduci('acquista', lingua)
      };

      this.cdr.detectChanges();
    });

    this.avviaTimer();
  }

  avviaTimer() {
    clearInterval(this.timer);

    this.timer = setInterval(() => {

      this.next();

      this.featuredIndex =
        (this.featuredIndex + 1) %
        this.prodottiEvidenza.length;

    }, 8000);
  }

  next() {

    if (this.isAnimating) return;

    this.isAnimating = true;

    this.currentIndex =
      (this.currentIndex + 1) %
      this.banners.length;

    setTimeout(() => this.isAnimating = false, 700);

    this.avviaTimer();
  }

  prev() {

    if (this.isAnimating) return;

    this.isAnimating = true;

    this.currentIndex =
      (this.currentIndex - 1 + this.banners.length) %
      this.banners.length;

    setTimeout(() => this.isAnimating = false, 700);

    this.avviaTimer();
  }

  goTo(index: number) {

    if (this.isAnimating || index === this.currentIndex)
      return;

    this.isAnimating = true;

    this.currentIndex = index;

    setTimeout(() => this.isAnimating = false, 700);

    this.avviaTimer();
  }

  getPosition(index: number): 'center' | 'left' | 'right' {

    const total = this.banners.length;

    const diff =
      (index - this.currentIndex + total) % total;

    if (diff === 0) return 'center';
    if (diff === 1) return 'right';

    return 'left';
  }

  nextFeatured() {

    this.featuredIndex =
      (this.featuredIndex + 1) %
      this.prodottiEvidenza.length;
  }

  prevFeatured() {

    this.featuredIndex =
      (this.featuredIndex - 1 + this.prodottiEvidenza.length)
      % this.prodottiEvidenza.length;
  }

  goToFeatured(index: number) {

    this.featuredIndex = index;

  }

  getFeaturedPosition(index: number): 'center' | 'left' | 'right' {

    const total = this.prodottiEvidenza.length;

    const diff =
      (index - this.featuredIndex + total) % total;

    if (diff === 0) return 'center';
    if (diff === 1) return 'right';

    return 'left';
  }

  ngOnDestroy() {

    clearInterval(this.timer);

    if (this.langSub)
      this.langSub.unsubscribe();
  }

}