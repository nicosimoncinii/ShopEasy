import { ApplicationConfig, provideBrowserGlobalErrorListeners, LOCALE_ID } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideIcons } from '@ng-icons/core';
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import {
  heroBuildingOffice2,
  heroEnvelope,
  heroPhone,
  heroClock,
  heroGlobeAlt,
  heroHeart
} from '@ng-icons/heroicons/outline';
import { heroHeartSolid } from '@ng-icons/heroicons/solid';
import { simpleFacebook, simpleInstagram } from '@ng-icons/simple-icons';
import { routes } from './app.routes';

registerLocaleData(localeIt);

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([
        (req, next) => {
          const token = localStorage.getItem('token'); 
          if (token) {
            req = req.clone({
              setHeaders: {
                Authorization: `Bearer ${token}`
              }
            });
          }
          return next(req);
        }
      ])
    ),
    { provide: LOCALE_ID, useValue: 'it-IT' },
  ]

};