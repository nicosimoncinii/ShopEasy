import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private temaAttuale = new BehaviorSubject<string>('light');
  tema$ = this.temaAttuale.asObservable();
  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    // Verifichiamo se siamo sul browser o sul server
    this.isBrowser = isPlatformBrowser(this.platformId);

    if (this.isBrowser) {
      // Se siamo sul browser, recuperiamo il tema salvato (se esiste)
      const savedTheme = localStorage.getItem('theme') || 'light';
      this.temaAttuale.next(savedTheme);
      this.applicaTema(savedTheme);
    }
  }

  toggleTema() {
    const nuovoTema = this.temaAttuale.getValue() === 'light' ? 'dark' : 'light';
    this.temaAttuale.next(nuovoTema);

    if (this.isBrowser) {
      localStorage.setItem('theme', nuovoTema);
      this.applicaTema(nuovoTema);
    }
  }

  private applicaTema(tema: string) {
    if (this.isBrowser) {
      const root = document.documentElement;
      if (tema === 'dark') {
        root.classList.add('dark-mode');
      } else {
        root.classList.remove('dark-mode');
      }
    }
  }

  getIsDarkMode(): boolean {
    return this.temaAttuale.getValue() === 'dark';
  }
}
