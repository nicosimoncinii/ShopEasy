import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';

@Component({
  selector: 'app-modal-login',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './modal-login.html',
  styleUrl: './modal-login.scss',
})
export class ModalLogin {

  @Output() chiudi = new EventEmitter<void>();

  constructor(
      private router: Router,
      public langService: LangService
  ) {}

  vaiAlLogin() {
    this.chiudi.emit();
    this.router.navigate(['/login']);
  }

  vaiAllaRegistrazione() {
    this.chiudi.emit();
    this.router.navigate(['/register']);
  }
}