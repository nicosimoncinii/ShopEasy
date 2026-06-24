import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from './components/navbar/navbar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Navbar], // <-- Controlla che ci siano entrambi!
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {}