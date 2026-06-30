import { Routes } from '@angular/router';

import { Home } from './pages/home/home';
import { Products } from './pages/products/products';
import { Login } from './pages/login/login';
import { RegisterComponent } from './pages/register/register'; // Corretto il nome dell'import
import { Cart } from './pages/cart/cart';
import { Wishlist } from './pages/wishlist/wishlist';
import {Checkout} from "./pages/checkout/checkout";
import { NotFoundComponent } from './pages/not-found/not-found';
import { PersonalArea } from './pages/personal-area/personal-area';
import { StoricoOrdini } from './pages/storico-ordini/storico-ordini'; // Importa il componente StoricoOrdini


export const routes: Routes = [
    { path: '', component: Home },
    { path: 'prodotti', component: Products },
    { path: 'login', component: Login },
    { path: 'register', component: RegisterComponent }, // Corretto il nome del componente
    { path: 'cart', component: Cart },
    { path: 'wishlist', component: Wishlist },
    { path: 'personal-area', component: PersonalArea },
    { path: 'storico-ordini', component: StoricoOrdini }, // Aggiunta la rotta per StoricoOrdini (roggi)
    { path: 'checkout', component: Checkout },
    { path: '**', component: NotFoundComponent }

];
