import { Component, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service'; 
import { LangService } from '../../services/lang.service'; 

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.scss']
})
export class RegisterComponent {
  private router = inject(Router);
  protected authService = inject(AuthService);
  protected langService = inject(LangService);

  nome: string = '';
  cognome: string = '';
  email: string = '';
  password: string = '';
  confermaPassword: string = '';

  errore = signal<string>('');
  caricamento = signal<boolean>(false);

  onRegister() {
    console.log('--- DIAGNOSTICA REGISTRAZIONE ---');
    console.log('Nome:', this.nome);
    console.log('Cognome:', this.cognome);
    console.log('Email:', this.email);
    console.log('Password inserita:', this.password ? 'Compilata' : 'Vuota');
    console.log('Conferma Password:', this.confermaPassword ? 'Compilata' : 'Vuota');

    // 1. Validazione lato Frontend (Disattivata temporaneamente per testare il Backend)
    // if (!this.nome || !this.cognome || !this.email || !this.password || !this.confermaPassword) {
    //   this.errore.set('Compila tutti i campi');
    //   return;
    // }
    if (this.password !== this.confermaPassword) {
      this.errore.set('Le password non coincidono');
      return;
    }
    if (this.password.length < 6) {
      this.errore.set('La password deve essere di almeno 6 caratteri');
      return;
    }

    this.caricamento.set(true);
    this.errore.set('');

    // 2. Chiamata HTTP post-validazione
    this.authService.register(this.nome, this.cognome, this.email, this.password, this.confermaPassword).subscribe({

      next: (risposta) => {
        this.caricamento.set(false);
        this.goToLogin();
      },
      error: (err) => {
        this.caricamento.set(false);
        console.error('Oggetto Errore dal backend:', err);
        
        // Estrattore avanzato per le risposte di errore di Spring Boot
        if (err.error) {
          // Caso A: Se Spring Boot mappa gli errori di validazione in un oggetto/mappa interno (es. err.error.errors o err.error)
          const erroreCorpo = err.error;
          
          if (typeof erroreCorpo === 'object') {
            // Controlla se c'è un messaggio diretto impostato da un controller personalizzato
            if (erroreCorpo.message) {
              this.errore.set(erroreCorpo.message);
              return;
            }
            
            // Cerca una mappa di errori nidificata (comportamento tipico di MethodArgumentNotValidException)
            const mappaErrori = erroreCorpo.errors || erroreCorpo;
            const chiavi = Object.keys(mappaErrori);
            
            if (chiavi.length > 0) {
              const primoErrore = mappaErrori[chiavi[0]];
              this.errore.set(typeof primoErrore === 'string' ? primoErrore : JSON.stringify(primoErrore));
              return;
            }
          }
          
          // Caso B: Se il backend restituisce una stringa di testo semplice
          if (typeof erroreCorpo === 'string') {
            this.errore.set(erroreCorpo);
            return;
          }
        }
        
        // Caso C: Fallback se lo status HTTP è 0 (server backend spento o irraggiungibile)
        if (err.status === 0) {
          this.errore.set('Impossibile connettersi al server backend. Controlla che Spring Boot sia attivo.');
          return;
        }

        // Fallback finale generico
        this.errore.set('Errore durante la registrazione. Riprova.');
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

  goBack() {
    this.router.navigate(['/']);
  }
}
