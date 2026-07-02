import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http'; // <-- Assicurati che ci sia HttpHeaders
import { switchMap } from 'rxjs/operators'; // <-- IMPORTANTE: Aggiungi questo per concatenare le chiamate API
import { LangService } from '../../services/lang.service';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.html',
  styleUrl: './checkout.scss'
})
export class Checkout {

  indirizzo: string = '';
  telefono: string = '';
  metodoPagamento: string = 'carta';

  numeroCarta: string = '';
  scadenza: string = '';
  cvv: string = '';
  emailPaypal: string = '';

  private http = inject(HttpClient);

  constructor(
      public langService: LangService,
      public cartService: CartService,
      private router: Router
  ) {}

  confermaOrdine() {
    if (!this.indirizzo || !this.telefono) {
      alert('Compila tutti i campi!');
      return;
    }

    // 1. Recupera il token JWT salvato nel localStorage
    const token = localStorage.getItem('token'); 
    if (!token) {
      alert('Sessione scaduta o utente non loggato. Effettua nuovamente il login.');
      return;
    }

    // 2. Configura gli headers con il Bearer Token (valido per entrambe le chiamate)
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // 3. Prepara la lista dei prodotti dal carrello
    const dettagliOrdine = this.cartService.getProdotti().map(prodotto => {
      return {
        prodottoId: prodotto.id,
        quantita: prodotto.quantita
      };
    });

    // 4. Flusso concatenato: Prima recupera l'utente, poi invia l'ordine
    this.http.get<any>('http://localhost:8080/api/users/me', { headers })
      .pipe(
        switchMap(utente => {
          // Ora abbiamo l'ID reale restituito dal database (utente.id)
          const payload = {
            utenteId: utente.id,
            dettagli: dettagliOrdine
          };
          
          // Passiamo la palla alla seconda richiesta POST, mantenendo gli stessi headers
          return this.http.post('http://localhost:8080/api/ordini', payload, { headers });
        })
      )
      .subscribe({
        next: (response) => {
          // Questo blocco viene eseguito quando la POST dell'ordine va a buon fine
          alert('Ordine effettuato con successo!');
          
          if (this.cartService.svuota) {
            this.cartService.svuota();
          }

          // Indirizza l'utente alla nuova pagina dello storico ordini
          this.router.navigate(['/storico-ordini']); 
        },
        error: (err) => {
          console.error('Errore durante la procedura di ordinazione:', err);
          if (err.status === 403) {
            alert('Errore 403: Permesso negato. Verifica la validità del token.');
          } else {
            alert('Si è verificato un errore durante l\'elaborazione dell\'ordine. Riprova.');
          }
        }
      });
  }
}