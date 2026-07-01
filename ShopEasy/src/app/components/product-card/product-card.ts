import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LangService } from '../../services/lang.service';
import { AuthService } from '../../services/auth.service';
// 1. IMPORTA IL COMPONENTE DELLE ICONE
import { NgIconComponent } from '@ng-icons/core';

@Component({
  selector: 'app-product-card',
  standalone: true,
  // 2. AGGIUNGI NgIconComponent QUI NEGLI IMPORTS
  imports: [CommonModule, NgIconComponent],
  templateUrl: './product-card.html',
  styleUrl: './product-card.scss'
})
export class ProductCard {
  @Input() product!: any;
  @Input() isInWishlist: boolean = false;

  @Output() aggiungiAlCarrello = new EventEmitter<any>();
  @Output() toggleWishlist = new EventEmitter<any>();
  @Output() nonLoggato = new EventEmitter<void>();

  constructor(
      public langService: LangService,
      private authService: AuthService
  ) {}

  onAggiungiAlCarrello() {
    if (this.authService.isLoggato()) {
      this.aggiungiAlCarrello.emit(this.product);
    } else {
      this.nonLoggato.emit();
    }
  }

  onToggleWishlist() {
    if (this.authService.isLoggato()) {
      this.toggleWishlist.emit(this.product);
    } else {
      this.nonLoggato.emit();
    }
  }
}