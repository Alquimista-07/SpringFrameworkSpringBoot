/* Archivo de pruebas */
/*NOTA: Como estamos trabajando con una base de datos H2 en desarrollo para las pruebas, es importante que creemos este archivo dentro de resources y lo 
        llamemos import.sql*/
/* Populate tables*/
INSERT INTO clientes (id, nombre, apellido, email, create_at) VALUES(1, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28');
INSERT INTO clientes (id, nombre, apellido, email, create_at) VALUES(2, 'Jhon', 'Doe', 'jhon@gmail.com', '2017-08-28');