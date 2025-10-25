package org.utl.dsm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.utl.dsm.cqrs.CQRSUsuario;
import org.utl.dsm.dao.DAOUsuario;
import org.utl.dsm.model.Usuario;

public class ControllerUsuario {

    public int insertarUsuario(Usuario usu) throws IllegalArgumentException, SQLException {
        try {
            CQRSUsuario cqrs = new CQRSUsuario();
            return cqrs.insertarUsuario(usu);
        } catch (IOException | ClassNotFoundException ex) {
            return -1;
        }
    }

    public int modificarUsuario(Usuario usu) throws IllegalArgumentException, SQLException {
        try {
            CQRSUsuario cqrs = new CQRSUsuario();
            return cqrs.modificarUsuario(usu);
        } catch (IOException | ClassNotFoundException ex) {
            return -1;
        }
    }

    public List<Usuario> getAllUsuariosActivos() throws SQLException, ClassNotFoundException, IOException {
        DAOUsuario daou = new DAOUsuario();
        List<Usuario> listaUsuarios = daou.getAllActivos();

        return listaUsuarios;
    }

    public List<Usuario> getAllUsuariosInactivos() throws SQLException, ClassNotFoundException, IOException {
        DAOUsuario daou = new DAOUsuario();
        List<Usuario> listaUsuarios = daou.getAllInactivos();

        return listaUsuarios;
    }

    public void activarUsuario(Usuario u) throws ClassNotFoundException, SQLException {
        CQRSUsuario cqrsu = new CQRSUsuario();
        cqrsu.activarUsuario(u);
    }

    public void eliminarUsuario(Usuario u) throws ClassNotFoundException, SQLException {
        CQRSUsuario cqrsu = new CQRSUsuario();
        cqrsu.eliminarUsuario(u);
    }
}
