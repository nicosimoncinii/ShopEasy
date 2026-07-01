import { Component, Input, Output, EventEmitter, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LangService } from '../../services/lang.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-card.html',
  styleUrl: './product-card.scss'
})
export class ProductCard {
  @Input() product!: any;
  @Input() isInWishlist: boolean = false;

  @Output() aggiungiAlCarrello = new EventEmitter<any>();
  @Output() toggleWishlist = new EventEmitter<any>();
  @Output() nonLoggato = new EventEmitter<void>();

  isPopupOpen = signal<boolean>(false);

  constructor(
      public langService: LangService,
      private authService: AuthService
  ) {}

  get disponibileProdotto(): boolean {
    if (!this.product) return true;
    const idUnico = this.product.id || this.product._id || 1;
    return (idUnico % 2 !== 0);
  }

  apriPopUp(event: Event) {
    event.preventDefault();
    event.stopPropagation();
    this.isPopupOpen.set(true);
  }

  chiudiPopUp(event: Event) {
    event.preventDefault();
    event.stopPropagation();
    this.isPopupOpen.set(false);
  }

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