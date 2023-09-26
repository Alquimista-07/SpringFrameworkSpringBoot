import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
//import { of } from 'rxjs';

import { HttpClient, HttpHeaders } from '@angular/common/http';

//import { CLIENTES } from '../components/clientes/clientes.json';
import { Cliente } from '../interfaces/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private urlEndpoint: string = 'http://localhost:8080/api/clientes';
  
  private httpHeaders = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor( private http: HttpClient ) { }

  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    return this.http.get<Cliente[]>(this.urlEndpoint);
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.urlEndpoint, cliente, {headers: this.httpHeaders});
  }

  getCliente(id: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndpoint}/${id}`);
  }

}
