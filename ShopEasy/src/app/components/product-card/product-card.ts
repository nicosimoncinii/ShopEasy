import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgIconComponent } from '@ng-icons/core';
import { Product } from '../../models/product';
import { LangService } from '../../services/lang.service';

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule, NgIconComponent],
  templateUrl: './product-card.html',
  styleUrl: './product-card.scss',
})

export class ProductCard {
  constructor(public langService: LangService){

  }
  @Input() product!: Product;
  @Output() aggiungiAlCarrello = new EventEmitter<Product>();
  @Output() toggleWishlist = new EventEmitter<Product>();
  @Input() isInWishlist: boolean = false;

  onAggiungiAlCarrello() {
    this.aggiungiAlCarrello.emit(this.product);
  }

  onToggleWishlist() {
    this.toggleWishlist.emit(this.product);
  }
}