import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';


@Component({
    selector: 'app-register',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './register.html',
    styleUrl: './register.scss'
})
export class Register {

    constructor(
        private router: Router,
    ) {}

    onRegister() { this.router.navigate(['/']); }
    goToLogin() { this.router.navigate(['/login']); }
    goBack() { this.router.navigate(['/']); }
}