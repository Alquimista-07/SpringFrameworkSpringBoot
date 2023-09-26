import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
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

  constructor(private http: HttpClient, private router: Router) { }

  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    return this.http.get<Cliente[]>(this.urlEndpoint);
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.urlEndpoint, cliente, { headers: this.httpHeaders })
      .pipe(
        catchError(err => {

          console.log(err.error.mensaje);

          Swal.fire(
            err.error.mensaje,
            err.error.error,
            'error'
          );

          return throwError(() => err);

        })
      );
  }

  getCliente(id: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndpoint}/${id}`)
      .pipe(
        catchError(err => {

          this.router.navigateByUrl("/clientes");
          console.log(err.error.mensaje);

          Swal.fire(
            'Error al editar',
            err.error.mensaje,
            'error'
          );

          return throwError(() => err);
        })
      );
  }

  update(cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.urlEndpoint}/${cliente.id}`, cliente, { headers: this.httpHeaders })
      .pipe(
        catchError(err => {

          console.log(err.error.mensaje);

          Swal.fire(
            err.error.mensaje,
            err.error.error,
            'error'
          );

          return throwError(() => err);

        })
      );
  }

  delete(id: number): Observable<Cliente> {
    return this.http.delete<Cliente>(`${this.urlEndpoint}/${id}`, { headers: this.httpHeaders })
      .pipe(
        catchError(err => {

          console.log(err.error.mensaje);

          Swal.fire(
            err.error.mensaje,
            err.error.error,
            'error'
          );

          return throwError(() => err);

        })
      );
  }

}
