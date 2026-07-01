import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ThemeService } from "../../services/theme.service";
import { LangService } from "../../services/lang.service";
import { UtenteService } from "../../services/utente.service"; // Assicurati che il percorso sia corretto

@Component({
  selector: "app-personal-area",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./personal-area.html",
  styleUrl: "./personal-area.scss",
})
export class PersonalArea implements OnInit {
  
  // L'oggetto conterrà le proprietà del DTO Java
  utente: any = {
    nome: "",
    cognome: "",
    telefono: "",
    indirizzo: "",
    languagePreference: "IT",
    themePreference: "LIGHT"
  };

  inModifica: { [key: string]: boolean } = {
    nome: false,
    cognome: false,
    telefono: false,
    indirizzo: false,
    languagePreference: false,
    themePreference: false
  };

  constructor(
    public themeService: ThemeService,
    public langService: LangService,
    private utenteService: UtenteService // Iniettiamo il servizio per il backend
  ) {}

  ngOnInit(): void {
    // Carica i dati reali dal database all'apertura della pagina
    this.utenteService.getProfiloCorrente().subscribe({
      next: (data) => {
        this.utente = data;
        
        // APPLICA LE PREFERENZE SALVATE NEL DB AL MOMENTO DELL'ACCESSO
        if (this.utente.languagePreference) {
          this.langService.caricaLingua(this.utente.languagePreference.toLowerCase());
        }
        if (this.utente.themePreference) {
          this.themeService.setTema(this.utente.themePreference.toLowerCase());
        }
      },
      error: (err) => {
        console.error("Errore nel recupero dei dati utente", err);
      }
    });
  }

  attivaModifica(campo: string) {
    this.inModifica[campo] = true;
  }

  salvaModifica(campo: string) {
    this.inModifica[campo] = false;

    // Costruiamo il DTO di richiesta per rispecchiare UtenteUpdateRequestDTO di Spring Boot
    const requestBody = {
      nome: this.utente.nome,
      cognome: this.utente.cognome,
      telefono: this.utente.telefono,
      indirizzo: this.utente.indirizzo,
      languagePreference: this.utente.languagePreference,
      themePreference: this.utente.themePreference
    };

    // Inviamo l'intero oggetto aggiornato al database
    this.utenteService.aggiornaProfiloCorrente(requestBody).subscribe({
      next: (response) => {
        console.log(`Campo ${campo} aggiornato nel DB con successo!`, response);
        this.utente = response; // Allineiamo l'interfaccia con i dati salvati

        // AGGIORNAMENTO ISTANTANEO DELLE PREFERENZE A SCHERMO DOPO IL SALVATAGGIO
        if (campo === 'languagePreference') {
          this.langService.caricaLingua(this.utente.languagePreference.toLowerCase());
        }
        if (campo === 'themePreference') {
          this.themeService.setTema(this.utente.themePreference.toLowerCase());
        }
      },
      error: (err) => {
        console.error("Errore durante il salvataggio sul database", err);
        alert("Impossibile salvare la modifica. Riprova.");
      }
    });
  }
}
