import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LangService } from '../../services/lang.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './not-found.html',
  styleUrl: './not-found.scss'
})
export class NotFoundComponent {

  constructor(
      public langService: LangService,
      private router: Router
  ) {}

  goHome() {
    this.router.navigate(['/']);
  }
}