import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { translations } from '../translations';

@Injectable({ providedIn: 'root' })
export class LanguageService {
  private lingua = new BehaviorSubject<string>('it');
  lingua$ = this.lingua.asObservable();

  cambia(lingua: string) {
    this.lingua.next(lingua);
  }

  traduci(chiave: string): string {
    return translations[this.lingua.getValue()][chiave] || chiave;
  }
}
