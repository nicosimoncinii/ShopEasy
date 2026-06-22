import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface CartItem {
  id: number;
  nome: string;
  prezzo: number;
  quantita: number;
}

@Injectable({ providedIn: 'root' })
export class CartService {
  private items = new BehaviorSubject<CartItem[]>([]);
  items$ = this.items.asObservable();

  // Stato apertura drawer laterale
  private drawerAperto = new BehaviorSubject<boolean>(false);
  drawerAperto$ = this.drawerAperto.asObservable();

  aggiungi(prodotto: { id: number; nome: string; prezzo: number }) {
    const correnti = this.items.getValue();
    const esistente = correnti.find(i => i.id === prodotto.id);
    if (esistente) {
      this.items.next(correnti.map(i =>
        i.id === prodotto.id ? { ...i, quantita: i.quantita + 1 } : i
      ));
    } else {
      this.items.next([...correnti, { ...prodotto, quantita: 1 }]);
    }
  }

  rimuovi(id: number) {
    this.items.next(this.items.getValue().filter(i => i.id !== id));
  }

  aumenta(id: number) {
    this.items.next(this.items.getValue().map(i =>
      i.id === id ? { ...i, quantita: i.quantita + 1 } : i
    ));
  }

  diminuisci(id: number) {
    const correnti = this.items.getValue();
    const item = correnti.find(i => i.id === id);
    if (!item) return;
    if (item.quantita <= 1) {
      this.rimuovi(id);
    } else {
      this.items.next(correnti.map(i =>
        i.id === id ? { ...i, quantita: i.quantita - 1 } : i
      ));
    }
  }

  get totaleQuantita(): number {
    return this.items.getValue().reduce((acc, i) => acc + i.quantita, 0);
  }

  get totalePrezzo(): number {
    return this.items.getValue().reduce((acc, i) => acc + i.prezzo * i.quantita, 0);
  }

  svuota() {
    this.items.next([]);
  }

  // --- Gestione drawer laterale ---
  apriDrawer() {
    this.drawerAperto.next(true);
  }

  chiudiDrawer() {
    this.drawerAperto.next(false);
  }

  toggleDrawer() {
    this.drawerAperto.next(!this.drawerAperto.getValue());
  }
}