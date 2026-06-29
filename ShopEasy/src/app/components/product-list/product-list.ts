import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductCard } from '../product-card/product-card';
import { CartService } from '../../services/cart.service';
import { WishlistService } from '../../services/wishlist.service';
import { Product } from '../../models/product';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, ProductCard],
  templateUrl: './product-list.html',
  styleUrl: './product-list.scss',
})
export class ProductList {

  constructor(
      public cartService: CartService,
      public wishlistService: WishlistService
  ) {}

  get products() {
    return this.cartService.prodottiTutti;
  }

  onAggiungiAlCarrello(product: Product) {
    this.cartService.aggiungiProdotto(product);
  }

  onToggleWishlist(product: Product) {
    if (this.wishlistService.isInWishlist(product.id)) {
      this.wishlistService.rimuovi(product.id);
    } else {
      this.wishlistService.aggiungi(product);
    }
  }
}