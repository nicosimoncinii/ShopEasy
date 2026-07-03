import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';
import { AuthService } from '../../services/auth.service';
import { LangService } from '../../services/lang.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.html',
  styleUrl: './admin.scss'
})
export class Admin implements OnInit {
  sezioneAttiva = signal<string>('dashboard');
  statistiche = signal<any>(null);
  ordini = signal<any[]>([]);
  prodotti = signal<any[]>([]);
  utenti = signal<any[]>([]);


  modaleAperta = signal<boolean>(false);
  nuovoProdotto = {
    nome: '',
    descrizione: '',
    prezzo: 0,
    quantita: 0,
    immagineUrl: '',
    categoriaId: 0
  };

  constructor(
      private adminService: AdminService,
      private authService: AuthService,
      public langService: LangService,
      private router: Router
  ) {}

  ngOnInit() {
    this.caricaStatistiche();
    this.caricaOrdini();
    this.caricaProdotti();
    this.caricaUtenti();
  }

  caricaStatistiche() {
    this.adminService.getStatistiche().subscribe({
      next: (data) => this.statistiche.set(data),
      error: (err) => console.error('Errore caricamento statistiche DTO', err)
    });
  }

  caricaOrdini() {
    this.adminService.getTuttiOrdini().subscribe({
      next: (data) => this.ordini.set(data),
      error: (err) => console.error('Errore ordini', err)
    });
  }

  caricaProdotti() {
    this.adminService.getTuttiProdotti().subscribe({
      next: (data) => this.prodotti.set(data),
      error: (err) => console.error('Errore prodotti', err)
    });
  }

  caricaUtenti() {
    this.adminService.getTuttiUtenti().subscribe({
      next: (data) => this.utenti.set(data),
      error: (err) => console.error('Errore utenti', err)
    });
  }

  cambiaSezione(sezione: string) {
    this.sezioneAttiva.set(sezione);
  }

  cancellaOrdine(id: number) {
    if (!confirm('Sei sicuro di voler cancellare questo ordine?')) return;
    this.adminService.cancellaOrdine(id).subscribe({
      next: () => {
        this.caricaOrdini();
        this.caricaStatistiche();
      },
      error: (err) => console.error('Errore cancella ordine', err)
    });
  }

  cancellaProdotto(id: number) {
    if (!confirm('Sei sicuro di voler cancellare questo prodotto?')) return;
    this.adminService.cancellaProdotto(id).subscribe({
      next: () => {
        this.caricaProdotti();
        this.caricaStatistiche();
      },
      error: (err) => console.error('Errore cancella prodotto', err)
    });
  }

  cancellaUtente(id: number) {
    if (!confirm('Sei sicuro di voler cancellare questo utente?')) return;
    this.adminService.cancellaUtente(id).subscribe({
      next: () => {
        this.caricaUtenti();
        this.caricaStatistiche();
      },
      error: (err) => console.error('Errore cancella utente', err)
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  apriModale() {
    this.modaleAperta.set(true);
  }

  chiudiModale() {
    this.modaleAperta.set(false);
    this.resetForm();
  }

  resetForm() {
    this.nuovoProdotto = {
      nome: '',
      descrizione: '',
      prezzo: 0,
      quantita: 0,
      immagineUrl: '',
      categoriaId: 0
    };
  }

  aggiungiProdotto() {
    if (!this.nuovoProdotto.nome || this.nuovoProdotto.categoriaId  <= 0 || this.nuovoProdotto.prezzo <= 0) {
      alert('Inserisci un nome, una categoria validi ed un prezzo valido!');
      return;
    }

    this.adminService.creaProdotto(this.nuovoProdotto).subscribe({
      next: () => {
        this.chiudiModale();
        this.caricaProdotti();     // Forza il ricaricamento dei prodotti reali
        this.caricaStatistiche();  // Prova ad aggiornare i contatori dal DTO
        alert('Prodotto aggiunto con successo!');
      },
      error: (err) => {
        console.error("Errore POST prodotto", err);
        alert("Errore 500 dal server. Il backend ha fallito l'aggiornamento del contatore delle statistiche.");
      }
    });
  }
}