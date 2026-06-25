import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  constructor(
      private router: Router,
      public langService: LangService
  ) {}

  onLogin() { this.router.navigate(['/']); }
  goToRegister() { this.router.navigate(['/register']); }
  goBack() { this.router.navigate(['/']); }
}