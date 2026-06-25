import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideIcons } from '@ng-icons/core';
import {
  heroBuildingOffice2,
  heroEnvelope,
  heroPhone,
  heroClock,
  heroGlobeAlt,
  heroHeart
} from '@ng-icons/heroicons/outline';
import {
  heroHeartSolid
} from '@ng-icons/heroicons/solid';
import {
  simpleFacebook,
  simpleInstagram
} from '@ng-icons/simple-icons';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideIcons({
      heroBuildingOffice2,
      heroEnvelope,
      heroPhone,
      heroClock,
      heroGlobeAlt,
      heroHeart,
      heroHeartSolid,
      simpleFacebook,
      simpleInstagram
    })
  ]
};