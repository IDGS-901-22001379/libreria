DROP DATABASE IF EXISTS usuarios;
CREATE DATABASE usuarios;
USE usuarios;

CREATE TABLE usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellidoPaterno VARCHAR(30) NOT NULL,
    apellidoMaterno VARCHAR(30) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    nombreUsuario VARCHAR(30) UNIQUE NOT NULL,
    contrasenia VARCHAR(60) NOT NULL,
    rol VARCHAR(20) NOT NULL, 		
    estatus INT NOT NULL,
    token LONGTEXT
);

CREATE TABLE libros (
    id_libro INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre_libro VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    genero VARCHAR(255) NOT NULL,
    estatus INT NOT NULL DEFAULT 1,
    archivo_pdf LONGTEXT NOT NULL,
    universidad VARCHAR(30) NOT NULL DEFAULT 'Universidad de Guadalajara'
);

DELIMITER //

CREATE PROCEDURE InsertarUsuario(
    IN p_nombre VARCHAR(30),
    IN p_apellidoPaterno VARCHAR(30),
    IN p_apellidoMaterno VARCHAR(30),
    IN p_telefono VARCHAR(15),
    IN p_rol VARCHAR(20),
    OUT p_idUsuario INT
)
BEGIN
    DECLARE v_consecutivo INT;
    DECLARE v_nombreUsuario VARCHAR(50);
    DECLARE v_contrasenia VARCHAR(50);

    -- Obtener el consecutivo basado en la cantidad de usuarios con el mismo rol
    SELECT COUNT(*) + 1 INTO v_consecutivo
    FROM usuario
    WHERE rol = p_rol;

    -- Generar el nombre de usuario y contraseña concatenando el rol, las primeras dos letras de nombre, apellidoPaterno, apellidoMaterno y el consecutivo
    SET v_nombreUsuario = CONCAT(p_rol, 
								UPPER(LEFT(p_nombre, 2)), 
								UPPER(LEFT(p_apellidoPaterno, 2)), 
								UPPER(LEFT(p_apellidoMaterno, 2)), 
                                v_consecutivo);

    -- La contraseña puede ser igual al nombre de usuario o personalizada
    SET v_contrasenia = v_nombreUsuario;

    -- Insertar el nuevo usuario en la tabla
    INSERT INTO usuario (nombre, apellidoPaterno, apellidoMaterno, telefono, rol, nombreUsuario, contrasenia, estatus)
    VALUES (p_nombre, p_apellidoPaterno, p_apellidoMaterno, p_telefono, p_rol, v_nombreUsuario, v_contrasenia, 1);

    -- Asignar el último ID insertado al parámetro de salida
    SET p_idUsuario = LAST_INSERT_ID();
END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE ModificarUsuario(
    IN p_idUsuario INT,
    IN p_nombre VARCHAR(30),
    IN p_apellidoPaterno VARCHAR(30),
    IN p_apellidoMaterno VARCHAR(30),
    IN p_telefono VARCHAR(15),
    IN p_nombreUsuario VARCHAR(30),
    IN p_contrasenia VARCHAR(60),
    IN p_rol VARCHAR(20),
    OUT p_idModificado INT
)
BEGIN
    -- Actualizar los datos del usuario, incluyendo rol, contraseña y nombre de usuario
    UPDATE usuario
    SET nombre = p_nombre,
        apellidoPaterno = p_apellidoPaterno,
        apellidoMaterno = p_apellidoMaterno,
        telefono = p_telefono,
        nombreUsuario = p_nombreUsuario,
        contrasenia = p_contrasenia,
        rol = p_rol
    WHERE idUsuario = p_idUsuario;

    -- Retornar el id del usuario modificado
    SET p_idModificado = p_idUsuario;
END //

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE InsertarLibro(
    IN p_nombre_libro VARCHAR(255),
    IN p_autor VARCHAR(255),
    IN p_genero VARCHAR(255),
    IN p_archivo_pdf LONGTEXT,
    OUT p_id_libro INT
)
BEGIN
    INSERT INTO libros (nombre_libro, autor, genero, archivo_pdf)
    VALUES (p_nombre_libro, p_autor, p_genero, p_archivo_pdf);
    
    -- Obtener el último ID insertado
    SET p_id_libro = LAST_INSERT_ID();
END $$

DELIMITER ;
DELIMITER $$

CREATE PROCEDURE ModificarLibro(
    IN p_id_libro INT,
    IN p_nombre_libro VARCHAR(255),
    IN p_autor VARCHAR(255),
    IN p_genero VARCHAR(255),
    IN p_archivo_pdf LONGTEXT,
    OUT p_id_modificado INT
)
BEGIN
    -- Actualiza el libro con los nuevos valores
    UPDATE libros
    SET 
        nombre_libro = p_nombre_libro,
        autor = p_autor,
        genero = p_genero,
        archivo_pdf = p_archivo_pdf
    WHERE id_libro = p_id_libro;
    
    -- Asigna el ID modificado al parámetro de salida
    SET p_id_modificado = p_id_libro;
END $$

DELIMITER ;
-- INSERT INTO libros (nombre_libro, autor, genero, estatus, archivo_pdf)
-- VALUES ('EJEMPLO.pdf', 'Autor Desconocido', 'Género Desconocido', 1, 'njdnjksnjsnj');

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, telefono, nombreUsuario, contrasenia, rol, estatus)
VALUES('Oscar', 'Gómez', 'Luna', '4771081527', 'admin', '1234', 'ADMIN', 1); 

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, telefono, nombreUsuario, contrasenia, rol, estatus)
VALUES('José', 'Pérez', 'López', '4774669301', 'biblio', '1234', 'BIBLI', 1); 

INSERT INTO usuario(nombre, apellidoPaterno, apellidoMaterno, telefono, nombreUsuario, contrasenia, rol, estatus)
VALUES('María', 'Martinez', 'Rivera', '4777452326', 'alumno', '1234', 'ALUMN', 1); 

SELECT * FROM usuario;
SELECT * FROM libros;
SELECT * FROM usuario WHERE nombreUsuario = 'admin' AND contrasenia = 1234;