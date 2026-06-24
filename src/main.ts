import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app'; // <-- NOTA: '.ts' è stato rimosso!

bootstrapApplication(App, appConfig)
    .catch((err) => console.error(err));