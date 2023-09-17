package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsadeideas.springboot.app.models.dao.service.IUploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {
	
	// Es necesario inyectar la clase service para el llamado a los metodos implementados
	// que hacen el borrado recursivo y creación del directorio uploads cada vez que se 
	// arranca la aplicación.
	@Autowired
	IUploadFileService uploadFileService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	// Este método propio de la interface CommandLineRunner permite que al ejecutar la aplicación
	// se inicialice y cree el directorio uploads automáticamente.
	// OJO esto borra y crea automáticamente el directorio cada vez que arranca la aplicación por lo 
	// tanto en un entorno productivo esto no va.
	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
		uploadFileService.init();
	}

}
