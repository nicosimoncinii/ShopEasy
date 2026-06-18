import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="login-container">
      <div class="login-card">
        <h2>Accedi a ShopEasy</h2>
        <form (ngSubmit)="onSubmit()">
          <div class="form-group">
            <label>Email</label>
            <input type="email" [(ngModel)]="email" name="email" required>
          </div>
          <div class="form-group">
            <label>Password</label>
            <input type="password" [(ngModel)]="password" name="password" required>
          </div>
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  `,
  styles: [`
    .login-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 70vh;
    }
    .login-card {
      background: var(--bg-card);
      color: var(--text-light);
      padding: 30px;
      border-radius: 12px;
      box-shadow: 0 4px 15px rgba(0,0,0,0.1);
      width: 100%;
      max-width: 400px;
      transition: background 0.3s, color 0.3s;
    }
    h2 { margin-bottom: 20px; text-align: center; }
    .form-group {
      margin-bottom: 15px;
      display: flex;
      flex-direction: column;
      gap: 5px;
    }
    input {
      padding: 10px;
      border-radius: 6px;
      border: 1px solid #ccc;
      outline: none;
    }
    button { width: 100%; margin-top: 10px; }
  `]
})
export class Login {
  email = '';
  password = '';

  onSubmit() {
    console.log('Login eseguito con:', this.email, this.password);
  }
}
