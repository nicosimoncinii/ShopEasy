// auth.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');

  const authReq = token ? req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`)
  }) : req;

  return next(authReq).pipe(
    catchError(err => {
      if (err.status === 401 || err.status === 403) {
        localStorage.removeItem('token');
        router.navigate(['/login']);
      }
      return throwError(() => err);
    })
  );
};