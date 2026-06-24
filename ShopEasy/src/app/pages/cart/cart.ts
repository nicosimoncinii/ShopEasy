import { Component } from '@angular/core';
import { CartService } from '../../services/cart.services';

@Component({
  selector: 'app-cart',
  imports: [],
  templateUrl: './cart.html',
  styleUrl: './cart.scss',
})
export class Cart {
  constructor(public cartService: CartService) {}
}