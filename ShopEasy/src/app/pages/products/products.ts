import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductList } from '../../components/product-list/product-list';
import { LangService } from '../../services/lang.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, ProductList],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products {
  constructor(public langService: LangService) {}
}