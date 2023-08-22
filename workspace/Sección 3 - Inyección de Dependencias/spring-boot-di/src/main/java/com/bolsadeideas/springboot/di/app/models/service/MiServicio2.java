package com.bolsadeideas.springboot.di.app.models.service;

import org.springframework.stereotype.Component;

/*
 * NOTA: Esta es una clase que se encarga de trabajar con los datos, de operar con lo que es lógica de negocio.
 *       Por lo general un servicio es una fachada de acceso a los objetos DAO, por ejemplo que realizan
 *       consultas, operaciones en las tablas de base de datos, independiente si se esta utilizando jdbc o 
 *       algún ORM (Hibernate con JPA) pero que acceden a los datos, realizan consultas, operaciones para
 *       implementar un CRUD, pero un service podría tener un atributo de un DAO o varios atributos y estos DAO
 *       podrían interactuar bajo una misma transacción en un método.
 */
/*
 * NOTA: En este ejemplo vamos a crear un servicio con el cual ahora ahora si se va a usar
 *       la inyección de dependencias, a diferencia de la primera clase llamada  MiServicio.
 *       
 *       Por lo tanto lo que tenemos que hacer es indicar que el componente se un
 *       componente de spring, es decir, lo tenemos que registrar en el contenedor
 *       y para esto podemos usar por ejemplo anotaciones, en este caso con @Component
 *       y ya con esto queda registrado como un Bean de Spring con el contexto por 
 *       defecto, es decir, que este objeto se crea una sola vez en nuestra aplicación,
 *       es un singleton, una sola instancia que se distribuye en la aplicación y que se
 *       puede inyectar en otros componentes de la aplicación. Pero algo IMPORTANTE es 
 *       que este dentro del package base que en este caso para este proyecto es el:
 *       com.bolsadeideas.springboot.di.app y el cual en dicho paquete es donde se
 *       encuentra la clase main (principal) anotada con SpringBootApplication por lo tanto
 *       todas las clases tienen que estar dentro de dicho paquete, incluyendo los servicios,
 *       controladores, entity, repository, DAO, es decir, todo, obviamente cada uno en sus
 *       respectivos package, pero como se indica los package o subcarpetas como controller,
 *       models, se desprenden o ramifican del package principal, es decir el package principal
 *       contiene los demás package con el fin de que quede organizado.
 */

/*
 * NOTA: Adicionalmente del @Component también podemos usar otra anotación la cual es el 
 *       un tipo de componente, el estereotipo llamado @Service y se puede usar cualquiera
 *       de las dos anotaciones, la única diferencia es que cuando se usa el @Service este
 *       aporta una semántica y nada más, es decir, algo descriptivo e indica que esta clase
 *       represena un servicio en Spring, es decir, una clase de lógica de negocio
 */

/*
 * NOTA: Como se había mencionado anteriormente que aún no estaba totalemten desacoplada
 *       ya que no usabamos interfaces, ni clases abstractas por lo tanto en este nuevo
 *       ejercicio ahora si vamos a implementar una interface para hacer ese desacoplamiento
 *       total.
 */

@Component("miServicioSimple")
public class MiServicio2 implements IServicio2 {
	
	/*
	 * NOTA: La anotación @Override indica que este método es una implementación de un
	 *       padre, ya sea una inreface o una clase que se este heredando.
	 *       
	 */
	/*
	 * NOTA: También tenemos que tener en cuenta que una clase componente Spring tienq que
	 *       tener ciertas características como por ejemplo tiene que tener siempre un 
	 *       constructor por defecto vacío ya que por ejemplo si tenemos una clase la cual 
	 *       tiene un constructor con parámetros además de ese constructor estamos obligados
	 *       a crear otro constructor sin argumentos, como se mencionó anteriormente vacío
	 *       ya que Spring por detrás de escena instancia estos objtos y los maneja de forma
	 *       automática y dichas instancias las realiza con el contructor sin parámetros, 
	 *       por lo tanto si tenemos solo el contructor con parámetros nos daría error
	 */
	@Override
	public String operacion() {
		return "Ejecutando algún proceso importante... Usando inyección de dependencias";
	}

}
