import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SearchService {
  private searchText = new BehaviorSubject<string>('');
  searchText$ = this.searchText.asObservable();

  cerca(testo: string) {
    this.searchText.next(testo);
  }
}
