import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductList } from '../../components/product-list/product-list';
import { LangService } from '../../services/lang.service';
import { ModalLogin } from '../../components/modal-login/modal-login';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, ProductList, ModalLogin],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products {
  // Il signal che dice ad Angular se mostrare o nascondere il modale di login
  mostraModal = signal<boolean>(false);

  constructor(public langService: LangService) {}
}