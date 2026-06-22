import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LanguageService } from '../../services/language.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login implements OnInit, OnDestroy {
  private langSub!: Subscription;

  vista: 'login' | 'register' = 'login';
  traduzioni: any = {};

  loginEmail = '';
  loginPassword = '';
  loginEmailTouched = false;
  loginPasswordTouched = false;
  loginSubmitted = false;
  loginSuccess = false;
  loginError = '';

  regNome = '';
  regCognome = '';
  regEmail = '';
  regPassword = '';
  regConfermaPassword = '';
  regNomeTouched = false;
  regCognomeTouched = false;
  regEmailTouched = false;
  regPasswordTouched = false;
  regConfermaPasswordTouched = false;
  regSubmitted = false;
  regSuccess = false;
  regError = '';

  constructor(
    public langService: LanguageService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.langSub = this.langService.lingua$.subscribe(lingua => {
      this.aggiornaTraduzione(lingua);
      this.cdr.detectChanges();
    });
  }

  aggiornaTraduzione(lingua: string) {
    this.traduzioni = {
      tabLogin:    this.langService.traduci('tabLogin', lingua),
      tabReg:      this.langService.traduci('tabReg', lingua),
      loginTitolo: this.langService.traduci('loginTitolo', lingua),
      loginSub:    this.langService.traduci('loginSub', lingua),
      loginBtn:    this.langService.traduci('loginBtn', lingua),
      regTitolo:   this.langService.traduci('regTitolo', lingua),
      regSub:      this.langService.traduci('regSub', lingua),
      regBtn:      this.langService.traduci('regBtn', lingua),
      nonHaiAccount:  this.langService.traduci('nonHaiAccount', lingua),
      haiAccount:     this.langService.traduci('haiAccount', lingua),
      passimenticata: this.langService.traduci('passimenticata', lingua),
      loginSuccesso:  this.langService.traduci('loginSuccesso', lingua),
      regSuccesso:    this.langService.traduci('regSuccesso', lingua),
    };
  }

  get loginEmailValida(): boolean {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.loginEmail);
  }
  get loginPasswordValida(): boolean {
    return this.loginPassword.length >= 6;
  }
  get loginFormValido(): boolean {
    return this.loginEmailValida && this.loginPasswordValida;
  }
  get regNomeValido(): boolean { return this.regNome.trim().length >= 2; }
  get regCognomeValido(): boolean { return this.regCognome.trim().length >= 2; }
  get regEmailValida(): boolean { return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.regEmail); }
  get regPasswordValida(): boolean { return this.regPassword.length >= 6; }
  get regPasswordHaMaiuscola(): boolean { return /[A-Z]/.test(this.regPassword); }
  get regPasswordHaNumero(): boolean { return /[0-9]/.test(this.regPassword); }
  get regConfermaValida(): boolean {
    return this.regPassword === this.regConfermaPassword && this.regConfermaPassword.length > 0;
  }
  get regFormValido(): boolean {
    return this.regNomeValido && this.regCognomeValido && this.regEmailValida &&
           this.regPasswordValida && this.regPasswordHaMaiuscola &&
           this.regPasswordHaNumero && this.regConfermaValida;
  }

  cambiaVista(v: 'login' | 'register') {
    this.vista = v;
    this.loginSubmitted = false;
    this.loginSuccess = false;
    this.loginError = '';
    this.regSubmitted = false;
    this.regSuccess = false;
    this.regError = '';
  }

  onLoginSubmit() {
    this.loginSubmitted = true;
    this.loginEmailTouched = true;
    this.loginPasswordTouched = true;
    if (!this.loginFormValido) return;
    console.log('Login:', this.loginEmail, this.loginPassword);
    this.loginSuccess = true;
  }

  onRegisterSubmit() {
    this.regSubmitted = true;
    this.regNomeTouched = true;
    this.regCognomeTouched = true;
    this.regEmailTouched = true;
    this.regPasswordTouched = true;
    this.regConfermaPasswordTouched = true;
    if (!this.regFormValido) return;
    console.log('Register:', this.regNome, this.regCognome, this.regEmail);
    this.regSuccess = true;
  }

  ngOnDestroy() {
    if (this.langSub) this.langSub.unsubscribe();
  }
}