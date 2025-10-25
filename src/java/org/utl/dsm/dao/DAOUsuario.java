package org.utl.dsm.dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.bd.ConexionMySQL;
import org.utl.dsm.model.Usuario;

public class DAOUsuario {

    public int insertarUsuario(Usuario u) throws ClassNotFoundException, SQLException, IOException {
        String query = "{CALL InsertarUsuario(?,?,?,?,?,?)}";
        ConexionMySQL conMysql = new ConexionMySQL();
        Connection conn = conMysql.abrirConexion();
        CallableStatement cstm = conn.prepareCall(query);

        cstm.setString(1, u.getNombre());
        cstm.setString(2, u.getApellidoPaterno());
        cstm.setString(3, u.getApellidoMaterno());
        cstm.setString(4, u.getTelefono());
        cstm.setString(5, u.getRol());

        cstm.registerOutParameter(6, Types.INTEGER);

        cstm.execute();

        u.setIdUsuario(cstm.getInt(6));
        cstm.close();
        conn.close();
        conMysql.cerrarConexion(conn);

        return u.getIdUsuario();
    }

    public int modificarUsuario(Usuario u) throws ClassNotFoundException, SQLException, IOException {
        String query = "{CALL ModificarUsuario(?,?,?,?,?,?,?,?,?)}";
        ConexionMySQL conMysql = new ConexionMySQL();
        Connection conn = conMysql.abrirConexion();
        CallableStatement cstm = conn.prepareCall(query);

        cstm.setInt(1, u.getIdUsuario());
        cstm.setString(2, u.getNombre());
        cstm.setString(3, u.getApellidoPaterno());
        cstm.setString(4, u.getApellidoMaterno());
        cstm.setString(5, u.getTelefono());
        cstm.setString(6, u.getNombreUsuario());
        cstm.setString(7, u.getContrasenia());
        cstm.setString(8, u.getRol());

        cstm.registerOutParameter(9, Types.INTEGER);

        cstm.execute();

        u.setIdUsuario(cstm.getInt(9));
        cstm.close();
        conn.close();
        conMysql.cerrarConexion(conn);

        return u.getIdUsuario();
    }

    public List<Usuario> getAllActivos() throws SQLException, ClassNotFoundException, IOException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        ConexionMySQL connMySQL = new ConexionMySQL();

        // 1. Crear la sentencia SQL
        String query = "SELECT * FROM usuario WHERE estatus = 1";
        // 2. Se establece la conexión con la BD
        Connection conn = connMySQL.abrirConexion();
        // 3. Se genera el statement para enviar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        // 4. Se prepara un ResultSet para obtener la respuesta de la BD
        ResultSet rs = pstmt.executeQuery();
        // 5. Recorrer el rs y extraer los datos
        while (rs.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setNombre(rs.getString("nombre"));
            u.setApellidoPaterno(rs.getString("apellidoPaterno"));
            u.setApellidoMaterno(rs.getString("apellidoMaterno"));
            u.setTelefono(rs.getString("telefono"));
            u.setNombreUsuario(rs.getString("nombreUsuario"));
            u.setContrasenia(rs.getString("contrasenia"));
            u.setEstatus(rs.getInt("estatus"));
            u.setRol(rs.getString("rol"));
            u.setToken(rs.getString("token"));

            listaUsuarios.add(u);
        }

        // 6. Cerrar todos los objetos
        rs.close();
        pstmt.close();
        conn.close();
        connMySQL.cerrarConexion(conn);

        // 7. Devolver la información
        return listaUsuarios;
    }

    public List<Usuario> getAllInactivos() throws SQLException, ClassNotFoundException, IOException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        ConexionMySQL connMySQL = new ConexionMySQL();

        // 1. Crear la sentencia SQL
        String query = "SELECT * FROM usuario WHERE estatus = 0";
        // 2. Se establece la conexión con la BD
        Connection conn = connMySQL.abrirConexion();
        // 3. Se genera el statement para enviar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        // 4. Se prepara un ResultSet para obtener la respuesta de la BD
        ResultSet rs = pstmt.executeQuery();
        // 5. Recorrer el rs y extraer los datos
        while (rs.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setNombre(rs.getString("nombre"));
            u.setApellidoPaterno(rs.getString("apellidoPaterno"));
            u.setApellidoMaterno(rs.getString("apellidoMaterno"));
            u.setTelefono(rs.getString("telefono"));
            u.setNombreUsuario(rs.getString("nombreUsuario"));
            u.setContrasenia(rs.getString("contrasenia"));
            u.setEstatus(rs.getInt("estatus"));
            u.setRol(rs.getString("rol"));
            u.setToken(rs.getString("token"));

            listaUsuarios.add(u);
        }

        // 6. Cerrar todos los objetos
        rs.close();
        pstmt.close();
        conn.close();
        connMySQL.cerrarConexion(conn);

        // 7. Devolver la información
        return listaUsuarios;
    }

    public void eliminar(Usuario u) throws ClassNotFoundException, SQLException {
        String query = "UPDATE usuario SET estatus = ? WHERE idUsuario = ?";

        // Establecer conexión y usar try-with-resources para cierre automático de recursos
        ConexionMySQL objConnMySql = new ConexionMySQL();
        try ( Connection conn = objConnMySql.abrirConexion();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Asignar valores a los parámetros
            pstmt.setInt(1, 0); // estatus = 0 (eliminado)
            pstmt.setInt(2, u.getIdUsuario());

            // Ejecutar la consulta
            pstmt.executeUpdate();
        }
    }

    public void activar(Usuario u) throws ClassNotFoundException, SQLException {
        String query = "UPDATE usuario SET estatus = ? WHERE idUsuario = ?";

        // Establecer conexión y usar try-with-resources para cierre automático de recursos
        ConexionMySQL objConnMySql = new ConexionMySQL();
        try ( Connection conn = objConnMySql.abrirConexion();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Asignar valores a los parámetros
            pstmt.setInt(1, 1); // estatus = 1 (activo)
            pstmt.setInt(2, u.getIdUsuario());

            // Ejecutar la consulta
            pstmt.executeUpdate();
        }
    }
}
