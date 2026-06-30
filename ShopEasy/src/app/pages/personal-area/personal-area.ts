import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ThemeService } from "../../services/theme.service";
import { LangService } from "../../services/lang.service"; // Aggiunto per le traduzioni dell'HTML

@Component({
  selector: "app-personal-area",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./personal-area.html",
  styleUrl: "./personal-area.scss",
})
export class PersonalArea {
  // Iniettiamo entrambi i servizi per renderli disponibili nel file HTML
  constructor(
    public themeService: ThemeService,
    public langService: LangService
  ) {}

  utente = {
    nome: "Mario",
    cognome: "Rossi",
    telefono: "+39 333 1234567",
    indirizzo: "Via Roma 10, Milano",
    linguaPreferita: "Italiano",
    temaGrafico: "Scuro"
  };

  inModifica: { [key: string]: boolean } = {
    nome: false,
    cognome: false,
    telefono: false,
    indirizzo: false,
    linguaPreferita: false,
    temaGrafico: false
  };

  attivaModifica(campo: string) {
    this.inModifica[campo] = true;
  }

  salvaModifica(campo: string) {
    this.inModifica[campo] = false;
  }
}
