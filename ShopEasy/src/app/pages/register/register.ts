import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  nome = '';
  cognome = '';
  email = '';
  password = '';
  confermaPassword = '';
  errore = signal('');
  caricamento = signal(false);

  constructor(
    private router: Router,
    public langService: LangService,
    private authService: AuthService
  ) {}

  onRegister() {
    if (!this.nome || !this.cognome || !this.email || !this.password || !this.confermaPassword) {
      this.errore.set('Compila tutti i campi');
      return;
    }
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

    this.authService.register(this.nome, this.cognome, this.email, this.password).subscribe({
      next: () => {
        this.caricamento.set(false);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errore.set(err.error?.message || 'Errore durante la registrazione');
        this.caricamento.set(false);
      }
    });
  }

  goToLogin() { this.router.navigate(['/login']); }
  goBack() { this.router.navigate(['/']); }
}