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
import org.utl.dsm.model.Libro;

public class DAOLibro {

    public int insertarLibro(Libro l) throws SQLException, IOException, ClassNotFoundException {
        String query = "{call InsertarLibro(?,?,?,?,?)}";
        ConexionMySQL connMysql = new ConexionMySQL();
        // se abre la conexion
        Connection conn = connMysql.abrirConexion();
        // crear el steatment  que llevara la consulta
        CallableStatement cstm = (CallableStatement) conn.prepareCall(query);
        //llenar todos los parametros de la llamada al procedure
        cstm.setString(1, l.getNombre_libro());
        cstm.setString(2, l.getAutor());
        cstm.setString(3, l.getGenero());
        cstm.setString(4, l.getArchivo_pdf());

        cstm.registerOutParameter(5, Types.INTEGER);

        cstm.execute();

        l.setId_libro(cstm.getInt(5));
        cstm.close();
        conn.close();
        connMysql.cerrarConexion(conn);
        return l.getId_libro();
    }

    public int modificarLibro(Libro l) throws SQLException, IOException, ClassNotFoundException {
        String query = "{call ModificarLibro(?,?,?,?,?,?)}";
        ConexionMySQL connMysql = new ConexionMySQL();
        // se abre la conexion
        Connection conn = connMysql.abrirConexion();
        // crear el steatment  que llevara la consulta
        CallableStatement cstm = (CallableStatement) conn.prepareCall(query);
        //llenar todos los parametros de la llamada al procedure
        cstm.setInt(1, l.getId_libro());
        cstm.setString(2, l.getNombre_libro());
        cstm.setString(3, l.getAutor());
        cstm.setString(4, l.getGenero());
        cstm.setString(5, l.getArchivo_pdf());

        cstm.registerOutParameter(6, Types.INTEGER);

        cstm.execute();

        l.setId_libro(cstm.getInt(6));
        cstm.close();
        conn.close();
        connMysql.cerrarConexion(conn);
        return l.getId_libro();
    }

    public List<Libro> getAllActivos() throws SQLException, ClassNotFoundException, IOException {
        List<Libro> listaLibros = new ArrayList<>();
        ConexionMySQL connMySQL = new ConexionMySQL();

        // 1. Crear la sentencia SQL
        String query = "SELECT * FROM libros WHERE estatus = 1";
        // 2. Se establece la conexión con la BD
        Connection conn = connMySQL.abrirConexion();
        // 3. Se genera el statement para enviar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        // 4. Se prepara un ResultSet para obtener la respuesta de la BD
        ResultSet rs = pstmt.executeQuery();
        // 5. Recorrer el rs y extraer los datos
        while (rs.next()) {
            Libro l = new Libro();
            l.setId_libro(rs.getInt("id_libro"));
            l.setNombre_libro(rs.getString("nombre_libro"));
            l.setAutor(rs.getString("autor"));
            l.setGenero(rs.getString("genero"));
            l.setEstatus(rs.getInt("estatus"));
            l.setArchivo_pdf(rs.getString("archivo_pdf"));
            l.setUniversidad(rs.getString("universidad"));

            listaLibros.add(l);
        }

        // 6. Cerrar todos los objetos
        rs.close();
        pstmt.close();
        conn.close();
        connMySQL.cerrarConexion(conn);

        // 7. Devolver la información
        return listaLibros;
    }

    public List<Libro> getAllInactivos() throws SQLException, ClassNotFoundException, IOException {
        List<Libro> listaLibros = new ArrayList<>();
        ConexionMySQL connMySQL = new ConexionMySQL();

        // 1. Crear la sentencia SQL
        String query = "SELECT * FROM libros WHERE estatus = 0";
        // 2. Se establece la conexión con la BD
        Connection conn = connMySQL.abrirConexion();
        // 3. Se genera el statement para enviar la consulta
        PreparedStatement pstmt = conn.prepareStatement(query);
        // 4. Se prepara un ResultSet para obtener la respuesta de la BD
        ResultSet rs = pstmt.executeQuery();
        // 5. Recorrer el rs y extraer los datos
        while (rs.next()) {
            Libro l = new Libro();
            l.setId_libro(rs.getInt("id_libro"));
            l.setNombre_libro(rs.getString("nombre_libro"));
            l.setAutor(rs.getString("autor"));
            l.setGenero(rs.getString("genero"));
            l.setEstatus(rs.getInt("estatus"));
            l.setArchivo_pdf(rs.getString("archivo_pdf"));
            l.setUniversidad(rs.getString("universidad"));

            listaLibros.add(l);
        }

        // 6. Cerrar todos los objetos
        rs.close();
        pstmt.close();
        conn.close();
        connMySQL.cerrarConexion(conn);

        // 7. Devolver la información
        return listaLibros;
    }

    public void eliminar(Libro l) throws ClassNotFoundException, SQLException {
        String query = "UPDATE libros SET estatus = ? WHERE id_libro = ?";

        // Establecer conexión y usar try-with-resources para cierre automático de recursos
        ConexionMySQL objConnMySql = new ConexionMySQL();
        try ( Connection conn = objConnMySql.abrirConexion();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Asignar valores a los parámetros
            pstmt.setInt(1, 0); // estatus = 0
            pstmt.setInt(2, l.getId_libro());

            // Ejecutar la consulta
            pstmt.executeUpdate();
        }
    }

    public void activar(Libro l) throws ClassNotFoundException, SQLException {
        String query = "UPDATE libros SET estatus = ? WHERE id_libro = ?";

        // Establecer conexión y usar try-with-resources para cierre automático de recursos
        ConexionMySQL objConnMySql = new ConexionMySQL();
        try ( Connection conn = objConnMySql.abrirConexion();  PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Asignar valores a los parámetros
            pstmt.setInt(1, 1); // estatus = 1
            pstmt.setInt(2, l.getId_libro());

            // Ejecutar la consulta
            pstmt.executeUpdate();
        }
    }

}
