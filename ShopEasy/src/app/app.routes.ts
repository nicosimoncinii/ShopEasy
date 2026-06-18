import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Products } from './pages/products/products';
import { Cart } from './pages/cart/cart';
import { Login } from './pages/login/login'; // <-- AGGIUNTO: Importa il componente del Login

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'prodotti', component: Products },
  { path: 'carrello', component: Cart },
  { path: 'login', component: Login }, // <-- AGGIUNTO: Rotta per andare su /login
];
