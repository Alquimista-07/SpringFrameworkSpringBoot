# SpringFramework - SpringBoot

## NOTAS DESPLIEGUE:

Para poder hacer el deploy de la aplicación, y construir el archivo ejecutable .jar debemos tener en cuanta lo siguiente:

* Si nos damos cuenta dentro del proyecto tenemos un archivo ejecutable llamado mvnw (Mave Wraper) el cual incluye un script que descarga Maven y envuelve las funcionalidades tipicas para generar un proyecto por ejemplo con maven install o maven package pero de una forma más simple con Spring Boot.

1. Primero lo que vamos a hacer es desde una consola de comandos dirigirnos a la ruta raiz del proyecto y ejecutar el siguiente comando:

En Windows:
```
.\mvnw.cmd package
```

En Linux y Mac
```
.\mvnw package
```

**NOTA:** Pero vamos a tener un pequeño problema si no tenemos configurada la variable de entorno JAVA_HOME y el path, por lo tanto es necesario configurarlas previamente ya sea en Windos, MAC o Linex y para lo cual podemos buscar un tutorial en google para ver como se configutar en cada sistema operativo.

2. Cuando en la consola se muestre BUILD SUCCESS, es decir que la construcción fue exitosa, simplemente lo que tenemos que hacer es levantar el proyecto o tomar el archivo jar para desplegarlo en el serviror.

Entonces para levantar (deploy) el proyecto de forma local ejecutamos el siguiente comando desde la consola y aplica para cualquier tipo de sistema operativo:

```
java -jar /target/nombre-proyecto-version.jar
```

**NOTA:** El archivo .jar queda generado dentro de la ruta ```\target\nombre-proyecto-version.jar```

3. Posteriormente ya podemos dirigirnos a la url del serviodor ya sea en la nube o local (localhost:8080).