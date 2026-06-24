import { ApplicationConfig } from '@angular/core';
import { provideRouter, withEnabledBlockingInitialNavigation } from '@angular/router';
import { provideClientHydration } from '@angular/platform-browser';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';   //spiegato su tablet

export const appConfig: ApplicationConfig = {
  providers: [
    // Configura le rotte e forza l'SSR ad aspettare il caricamento iniziale
    provideRouter(routes, withEnabledBlockingInitialNavigation()),

    // Necessario per l'SSR (Hydration attiva per evitare il flash della pagina)
    provideClientHydration(),
    provideHttpClient(),  //spiegato su tablet
  ]
};