import { Injectable, Inject, PLATFORM_ID, signal } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private temaAttuale = signal<string>('light');
  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    this.isBrowser = isPlatformBrowser(this.platformId);

    if (this.isBrowser) {
      const savedTheme = localStorage.getItem('theme') || 'light';
      this.temaAttuale.set(savedTheme);
      this.applicaTema(savedTheme);
    }
  }

  toggleTema() {
    const nuovoTema = this.temaAttuale() === 'light' ? 'dark' : 'light';
    this.temaAttuale.set(nuovoTema);

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
    return this.temaAttuale() === 'dark';
  }

  getTemaSignal() {
    return this.temaAttuale.asReadonly();
  }
  
  setTema(nuovoTema: string) {
    this.temaAttuale.set(nuovoTema);

    if (this.isBrowser) {
      localStorage.setItem('theme', nuovoTema);
      this.applicaTema(nuovoTema);
    }
  }


}