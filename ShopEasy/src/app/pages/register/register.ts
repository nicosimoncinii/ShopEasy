import { Component, inject, signal } from '@angular/core';
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

  // Variabili per i campi del form
  nome = '';
  cognome = '';
  email = '';
  password = '';
  confermaPassword = '';

  // Segnali usati dal tuo HTML per gestire lo stato della pagina
  errore = signal<string | null>(null);
  caricamento = signal<boolean>(false);

  // Funzione chiamata dal click sul pulsante "Registrati"
  onRegister() {
    this.errore.set(null); // Resetta eventuali errori precedenti

    // Controllo base sulle password
    if (this.password !== this.confermaPassword) {
      this.errore.set('Le password non coincidono!');
      return;
    }

    // Controllo che i campi non siano vuoti
    if (!this.nome || !this.cognome || !this.email || !this.password) {
      this.errore.set('Tutti i campi sono obbligatori.');
      return;
    }

    this.caricamento.set(true);

    // Aggiunto .toLowerCase() a this.email
    this.authService.register(
      this.nome, 
      this.cognome, 
      this.email.toLowerCase(), 
      this.password, 
      this.confermaPassword
    ).subscribe({
      next: (risposta) => {
        this.caricamento.set(false);
        // Puoi usare il messaggio che arriva direttamente dal backend
        console.log(risposta.messaggio || 'Registrazione avvenuta con successo!'); 
        
        // Visto che l'API non fornisce un token, lo mandiamo al login
        this.router.navigate(['/login']); 
      },
      error: (err) => {
        this.caricamento.set(false);
        console.error('Errore durante la registrazione:', err);
        this.errore.set('Errore durante la registrazione. Riprova più tardi.');
      }
    });
  }

  // Funzione per andare al login
  goToLogin() {
    this.router.navigate(['/login']);
  }

  // Funzione per tornare indietro
  goBack() {
    this.router.navigate(['/']); // O ovunque tu preferisca rimandare l'utente
  }
}
