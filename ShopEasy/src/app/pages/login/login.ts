import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
 imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  email = '';
  password = '';
  errore = signal('');
  caricamento = signal(false);

  constructor(
    private router: Router,
    public langService: LangService,
    private authService: AuthService
  ) {}

  onLogin() {
    if (!this.email || !this.password) {
      this.errore.set('Compila tutti i campi');
      return;
    }
    this.caricamento.set(true);
    this.errore.set('');

    this.authService.login(this.email, this.password).subscribe({
      next: (res) => {
        this.authService.salvaToken(res.token);
        this.caricamento.set(false);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.errore.set('Email o password errati');
        this.caricamento.set(false);
      }
    });
  }

  goToRegister() { this.router.navigate(['/register']); }
  goBack() { this.router.navigate(['/']); }
}