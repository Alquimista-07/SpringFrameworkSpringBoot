package com.bolsadeideas.springboot.app.models.dao.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	// Creamos un logger para hacer debug de los nombres de directorio en la consola en el método de guardar
	private final Logger log = LoggerFactory.getLogger(getClass());
		
	// Constante para almacenar el nombre del folder donde se guardan los archivos
	private final static String UPLOADS_FOLDER = "uploads";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		
		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);
		Resource recurso = null;
		
		recurso = new UrlResource(pathFoto.toUri());
			
		if (!recurso.exists() && !recurso.isReadable() ) {
			throw new RuntimeException("Error: No se puede cargar la imagen: " + pathFoto.toString());
		}
		
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		//----------------------------------------------------------------------
		// PRIMER MÉTODO PARA MANEJAR LA RUTA DE LA CARGA DE IMÁGENES
		//----------------------------------------------------------------------
		// NOTA: Comentamos esto ya que vamos a pasar de un directorio dentro del proyecto a otro directorio
		//       fuera de este en otra ubicación.
		// Path directorioRecursos = Paths.get("src/main/resources/static/uploads");
		// String rootPath = directorioRecursos.toFile().getAbsolutePath();
		
		//----------------------------------------------------------------------
		// SEGUNDO MÉTODO PARA MANEJAR LA RUTA DE LA CARGA DE IMÁGENES
		//----------------------------------------------------------------------
		// NOTA: Esto es para windows, pero por ejemplo en linux pasariamos 
		//       la ubicación como "file:/opt/uploads/"
		//       físico.
		// NOTA: Comentamos esto para cambiar por un directorio absoluto en la raiz del mismo proyecto,
        //       ya que este hace referencia a un directorio dentro del servidor.
		//String rootPath = "C://Temp//uploads";
		//System.out.println("Entro aca: " + rootPath);
		
		//----------------------------------------------------------------------
		// TERCER MÉTODO PARA MANEJAR LA RUTA DE LA CARGA DE IMÁGENES
		//----------------------------------------------------------------------
		// Y generamos un uid aleatorio para que no se repitan los nombres dentro del servidor y se
		// puedan sobreescribir archivos por error.
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename);
		
		// Log
		log.info("rootPath: " + rootPath);
		
		// Comentamos esto para cambiarlo por el método Files.copy() para la 
		// copia o guardado del archivo en vez de obtener lo bytes
		//----------------------------------------------------------------------
		// Obtenemos los bytes de la imágen
		//byte[] bytes = foto.getBytes();
			
		// Obtenemos la ruta final con el nombre del archivo
		//Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
			
		// Gudardamos la foto
		//Files.write(rutaCompleta, bytes);
		//----------------------------------------------------------------------
			
		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		// Obtenemos la ruta absoluta de la imágen para eliminarla
		Path rootPath = getPath(filename);
		// Obtenemos el archivo
		File archivo = rootPath.toFile();
					
		if ( archivo.exists() && archivo.canRead() ) {
			// Se elimina
			if (archivo.delete() ) {
				return true;
			}
		}
		
		return false;
	}
	
	public Path getPath( String filename ) {
		// Ruta absoluta
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	// Métodos para borrar todo el directorio uploads de forma recursiva
	// OJO esto en entorno productivo no va ya que borra todo.
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}

}
