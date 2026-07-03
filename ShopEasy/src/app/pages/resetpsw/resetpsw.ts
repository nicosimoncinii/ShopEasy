import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service'; 
import { AuthService } from '../../services/auth.service'; 
import { timeout } from 'rxjs';

@Component({
  selector: 'app-resetpsw',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './resetpsw.html',   
  styleUrls: ['./resetpsw.scss']       
})
export class ResetpswComponent {
  email: string = '';
  isCaricamento: boolean = false;
  messaggioErrore: string = '';
  messaggioSuccesso: string = '';

  constructor(
    private router: Router,
    public langService: LangService,
    private authService: AuthService 
  ) {}

  onInvia() {
    if (!this.email || !this.email.includes('@')) {
      this.messaggioErrore = 'Inserisci un indirizzo email valido.';
      this.messaggioSuccesso = '';
      return;
    }

    this.isCaricamento = true;
    this.messaggioErrore = '';
    this.messaggioSuccesso = '';

    this.authService.forgotPassword(this.email)
      .pipe(timeout(5000)) 
      .subscribe({
        next: (risposta) => {
          console.log('Risposta dal server:', risposta);
          this.isCaricamento = false; // Sblocca il tasto se va a buon fine
          this.messaggioSuccesso = 'Email di ripristino inviata con successo!';
          this.email = ''; 
        },
        error: (errore) => {
          console.error('Errore durante il reset:', errore);
          this.isCaricamento = false; // Sblocca il tasto se va in errore o in timeout

          if (errore.error && errore.error.message) {
            this.messaggioErrore = errore.error.message;
          } else {
            this.messaggioErrore = 'Il server ha impiegato troppo tempo a rispondere. Controlla il servizio email in Java.';
          }
        }
      });
  }

  tornaAlLogin() {
    this.router.navigate(['/login']);
  }
}
