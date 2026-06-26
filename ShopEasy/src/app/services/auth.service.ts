import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, password });
  }

  register(nome: string, cognome: string, email: string, password: string, confermaPassword: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, { nome, cognome, email, password, confermaPassword });
  }

  salvaToken(token: string) {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggato(): boolean {
    return !!this.getToken();
  }

  logout() {
    localStorage.removeItem('token');
  }

  private getAuthHeaders() {
    const token = this.getToken();
    return { headers: { Authorization: `Bearer ${token}` } };
  }

  getProfile(): Observable<any> {
    return this.http.get('http://localhost:8080/api/users/me', this.getAuthHeaders());
  }

  updateProfile(data: any): Observable<any> {
    return this.http.put('http://localhost:8080/api/users/me', data, this.getAuthHeaders());
  }

  changePassword(password: string): Observable<any> {
    return this.http.put('http://localhost:8080/api/users/me/password', { password }, this.getAuthHeaders());
  }

  deleteAccount(): Observable<any> {
    return this.http.delete('http://localhost:8080/api/users/me', this.getAuthHeaders());
  }

  getMyOrders(): Observable<any> {
    return this.http.get('http://localhost:8080/api/ordini/me', this.getAuthHeaders());
  }
}