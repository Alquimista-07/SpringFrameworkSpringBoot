import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

// Sweet Alert
import Swal from 'sweetalert2';

import { Cliente } from 'src/app/interfaces/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  public titulo = 'Crear Cliente';

  constructor(private clienteService: ClienteService, private router: Router, private activatedRoute: ActivatedRoute) { }
  public cliente: Cliente = {
    nombre: '',
    apellido: '',
    email: ''
  };

  ngOnInit(): void {
    this.cargarCliente();
  }

  public create(): void {
    this.clienteService.create(this.cliente).subscribe(
      resp => {
        this.router.navigateByUrl("/clientes")
        Swal.fire(
          'Nuevo Cliente',
          `El cliente ${this.cliente.nombre} se ha creado con éxito!`,
          'success'
        )
      }
    );
  }

  public cargarCliente(): void {
    this.activatedRoute.params.subscribe(({ id }) => {
      if (id) {
        this.clienteService.getCliente(id)
            .subscribe( cliente => this.cliente = cliente)
      }
    });
  }

}
