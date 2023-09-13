package com.bolsadeideas.springboot.app.models.dao.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	public Resource load( String filename ) throws MalformedURLException;
	
	public String copy( MultipartFile file ) throws IOException;
	
	public boolean delete( String file );
	
	// Métodos para borrar todo el directorio uploads de forma recursiva
	// OJO esto en entorno productivo no va ya que borra todo.
	public void deleteAll();
	
	public void init() throws IOException;
	
}
