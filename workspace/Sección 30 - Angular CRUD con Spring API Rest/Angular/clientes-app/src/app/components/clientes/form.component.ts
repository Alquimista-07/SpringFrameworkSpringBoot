import { Component } from '@angular/core';
import { Cliente } from 'src/app/interfaces/cliente';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent {

  public titulo = 'Crear Cliente';
  
  public cliente: Cliente = {
    nombre: '',
    apellido: '',
    email: ''
  };

  public create(): void {
    console.log("Clicked");
    console.log(this.cliente);
  }

}
