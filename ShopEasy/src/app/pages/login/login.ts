import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LangService } from '../../services/lang.service';

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
  messaggioErrore = '';    
  isCaricamento = false;  

  constructor(
      private router: Router,
      public langService: LangService
  ) {}

  onLogin() { 
    this.router.navigate(['/']); 
  }
  
  goToRegister() { 
    this.router.navigate(['/register']); 
  }
  
  goBack() { 
    this.router.navigate(['/']); 
  }
  
  goToResetPassword() { 
    // CORREZIONE: Usa lo stesso path definito in app.routes.ts
    this.router.navigate(['/resetpsw']); 
  }
}
