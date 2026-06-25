import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class LangService {

    private traduzioni = signal<any>({});
    private lingua = 'it';

    constructor(private http: HttpClient) {
        this.caricaLingua('it');
    }

    caricaLingua(lingua: string) {
        this.lingua = lingua;
        this.http.get(`/i18n/${lingua}.json`)
            .subscribe(dati => this.traduzioni.set(dati));
    }

    getLingua(): string {
        return this.lingua;
    }

    traduci(chiave: string): string {
        return this.traduzioni()[chiave] || chiave;
    }
}