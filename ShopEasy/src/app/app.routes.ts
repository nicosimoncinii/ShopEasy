import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Products } from './pages/products/products';
import { Cart } from './pages/cart/cart';
import { Checkout } from './pages/checkout/checkout';
import { Login } from './pages/login/login';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'prodotti', component: Products },
  { path: 'carrello', component: Cart },
  { path: 'checkout', component: Checkout },
  { path: 'login', component: Login },
];