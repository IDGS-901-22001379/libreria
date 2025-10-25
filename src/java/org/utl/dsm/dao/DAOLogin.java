package org.utl.dsm.dao;

import java.io.IOException;
import org.utl.dsm.model.Usuario;
import java.sql.SQLException;
import java.sql.Connection;
import org.utl.dsm.bd.ConexionMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOLogin {
    public Usuario buscarUsuario(String nombreUsuario, String contrasenia) throws SQLException, ClassNotFoundException, IOException {
        String querySELECT = "SELECT * FROM usuario WHERE nombreUsuario = ? AND contrasenia = ?";
        ConexionMySQL objConn = new ConexionMySQL();

        try (Connection conn = objConn.abrirConexion();  PreparedStatement pstmt = conn.prepareStatement(querySELECT)) {

            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, contrasenia);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellidoP = rs.getString("apellidoPaterno");
                String apellidoM = rs.getString("apellidoMaterno");
                String telefono = rs.getString("telefono");
                String nombreU = rs.getString("nombreUsuario");
                String clave = rs.getString("contrasenia");
                String rol = rs.getString("rol");
                int estatus = rs.getInt("estatus");

                return new Usuario(idUsuario, nombre, apellidoP, apellidoM, telefono, nombreU, clave, rol, estatus, null);
            }
        }
        return new Usuario(-1, "", "", "", "", "", "", "", 0, null);
    }

    public void actualizarToken(String nombreUsuario, String contrasenia, String token) throws SQLException, ClassNotFoundException, IOException {
        String queryUPDATE = "UPDATE usuario SET token = ? WHERE nombreUsuario = ? AND contrasenia = ?";
        ConexionMySQL objConn = new ConexionMySQL();

        try ( Connection conn = objConn.abrirConexion();  PreparedStatement updateStatement = conn.prepareStatement(queryUPDATE)) {

            updateStatement.setString(1, token);
            updateStatement.setString(2, nombreUsuario);
            updateStatement.setString(3, contrasenia);
            updateStatement.executeUpdate();
        }
    }
}
