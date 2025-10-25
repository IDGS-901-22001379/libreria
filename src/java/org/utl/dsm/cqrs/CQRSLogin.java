package org.utl.dsm.cqrs;

import java.io.IOException;
import java.sql.SQLException;
import org.utl.dsm.dao.DAOLogin;

public class CQRSLogin {
    public void actualizarToken(String nombreUsuario, String contrasenia, String token) throws SQLException, ClassNotFoundException, IOException {
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new IllegalArgumentException("Nombre de usuario no válido");
        }
        if (contrasenia  == null || contrasenia.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        } 
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("El token no puede estar vacío");
        }
        
        DAOLogin daol = new DAOLogin();
        daol.actualizarToken(nombreUsuario, contrasenia, token);
    }
}
