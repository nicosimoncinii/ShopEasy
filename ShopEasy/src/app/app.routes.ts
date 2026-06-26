import { Routes } from '@angular/router';

import { Home } from './pages/home/home';
import { Products } from './pages/products/products';
import { Login } from './pages/login/login';
import { RegisterComponent } from './pages/register/register'; // Corretto il nome dell'import
import { Cart } from './pages/cart/cart';
import { Wishlist } from './pages/wishlist/wishlist';
import { NotFoundComponent } from './pages/not-found/not-found';
import { AccountComponent } from './pages/account/account';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: '', component: Home },
    { path: 'prodotti', component: Products },
    { path: 'login', component: Login },
    { path: 'register', component: RegisterComponent }, // Corretto il nome del componente
    { path: 'cart', component: Cart },
    { path: 'wishlist', component: Wishlist },
    { path: 'account', component: AccountComponent, canActivate: [authGuard] },
    { path: '**', component: NotFoundComponent }
];
