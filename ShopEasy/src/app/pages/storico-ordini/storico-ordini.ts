import { ChangeDetectorRef, Component, OnInit, inject } from "@angular/core";
import { CommonModule } from "@angular/common";
import { HttpClient, HttpHeaders } from "@angular/common/http"; // <-- IMPORTANTE: aggiunto HttpHeaders

export interface Categoria {
  id: number;
  nome: string;
}

export interface Prodotto {
  id: number;
  nome: string;
  descrizione: string;
  prezzo: number;
  quantita: number;
  immagine: string;
  categoria: Categoria;
}

export interface DettaglioOrdine {
  id: number;
  prodotto: Prodotto;
  quantita: number;
  prezzoUnitario: number;
}

export interface Ordine {
  id: number;
  data: string;
  stato: string;
  totale: number;
  utenteId: number;
  utenteNome: string;
  dettagli: DettaglioOrdine[];
}

@Component({
  selector: "app-storico-ordini",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./storico-ordini.html",
  styleUrl: "./storico-ordini.scss",
})
export class StoricoOrdini implements OnInit {
  ordini: Ordine[] = [];
  loading: boolean = true;
  errorMessage: string = '';
  private cdr = inject(ChangeDetectorRef);

  private http = inject(HttpClient);

  ngOnInit(): void {
    this.caricaStoricoOrdini();
  }

  caricaStoricoOrdini(): void {
    // 1. Recupera il token di accesso
    const token = localStorage.getItem('token'); 
    
    // 2. Costruisci l'intestazione con il token
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    } else {
      // Se non c'è il token, blocca il caricamento e avvisa l'utente
      this.errorMessage = 'Non sei autenticato. Effettua il login per vedere i tuoi ordini.';
      this.loading = false;
      return;
    }

    
    // 3. Fai la chiamata API all'indirizzo completo della porta 8080 includendo gli headers
    this.http.get<Ordine[]>('http://localhost:8080/api/ordini/my-orders', { headers }).subscribe({
      next: (data) => {
        //debugger;
        this.ordini = data;
        console.log(this.ordini);
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        //debugger;
        console.error('Errore nel recupero degli ordini', err);
        if (err.status === 403) {
          this.errorMessage = 'Sessione scaduta o permesso negato. Effettua nuovamente il login.';
        } else {
          this.errorMessage = 'Si è verificato un errore nel caricamento del tuo storico ordini. Riprova più tardi.';
        }
        this.loading = false; // Ferma lo spinner di caricamento anche in caso di errore
      }
    });
  }
}