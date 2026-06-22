import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './components/navbar/navbar';
import { CartDrawer } from './components/cart-drawer/cart-drawer';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar, CartDrawer],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {}