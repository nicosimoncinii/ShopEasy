import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductCard } from '../product-card/product-card';
import { Product } from '../../models/product';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, ProductCard],
  templateUrl: './product-list.html',
  styleUrl: './product-list.scss',
})
export class ProductList {
  products: Product[] = [
    {
      id: 1,
      name: 'iPhone 16 Pro',
      imageUrl: 'https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-16-pro-natural-titanium-select-202409?wid=5120&hei=2880&fmt=webp&qlt=70&.v=1724263624838',
      price: 651.00,
      listPrice: 1229.00,
      rating: 5,
      reviewsCount: 342,
      colors: ['#8f8f8e', '#f5f5f4', '#3b3b3c', '#d1bda1']
    },
    {
      id: 2,
      name: 'iPhone 15',
      imageUrl: 'https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-15-black-select-202309?wid=5120&hei=2880&fmt=webp&qlt=70&.v=1692875591965',
      price: 549.00,
      listPrice: 979.00,
      rating: 4.5,
      reviewsCount: 1205,
      colors: ['#3b3b3c', '#e4e7ed', '#cfd9c9', '#eaddce', '#e3c8ca']
    },
    {
      id: 3,
      name: 'iPhone 14 Pro',
      imageUrl: 'https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-14-pro-model-unselect-gallery-2-202209_GEO_US?wid=5120&hei=2880&fmt=p-jpg&qlt=80&.v=1660753617560',
      price: 499.00,
      listPrice: 899.00,
      rating: 4,
      reviewsCount: 890,
      colors: ['#4b4845', '#e3e4e5', '#f2e8d9', '#594f63']
    },
    {
      id: 4,
      name: 'iPhone 13',
      imageUrl: 'https://store.storeimages.cdn-apple.com/4668/as-images.apple.com/is/iphone-13-model-unselect-gallery-2-202207_GEO_US?wid=5120&hei=2880&fmt=p-jpg&qlt=80&.v=1654897073372',
      price: 350.00,
      listPrice: 629.00,
      rating: 4.8,
      reviewsCount: 2310,
      colors: ['#42474d', '#f9f6f0', '#25282a', '#3f4b3b', '#bf0013', '#f2d3ce']
    }
  ];
}
