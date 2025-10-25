package org.utl.dsm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.utl.dsm.cqrs.CQRSLogin;
import org.utl.dsm.dao.DAOLogin;
import org.utl.dsm.model.Usuario;

public class ControllerLogin {

    public Usuario login(String nombreUsuario, String contrasenia) throws ClassNotFoundException, SQLException, IOException, IllegalArgumentException {
        DAOLogin daol = new DAOLogin();
        Usuario usuario = daol.buscarUsuario(nombreUsuario, contrasenia);

        CQRSLogin cqrsl = new CQRSLogin();
        String token = generarToken(nombreUsuario);
        cqrsl.actualizarToken(nombreUsuario, contrasenia, token);
        usuario.setToken(token);
        return usuario;
    }

    // Método para generar el token
    public String generarToken(String nombreUsuario) {
        String token = "";
        try {
            Date myDate = new Date();
            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(myDate);
            String cadena = nombreUsuario + "." + "BIBLIOTECA" + "." + fecha;
            token = DigestUtils.md5Hex(cadena);
            System.out.println("Token generado: " + token);
        } catch (Exception e) {
            System.out.println("Error al generar el token: " + e.getMessage());
        }
        return token;
    }
}
