package org.utl.dsm.cqrs;

import java.sql.SQLException;
import java.io.IOException;
import org.utl.dsm.dao.DAOUsuario;
import org.utl.dsm.model.Usuario;

public class CQRSUsuario {
    public int insertarUsuario(Usuario u) throws SQLException, IOException, ClassNotFoundException {
        // Validación del nombre
        if (u.getNombre() == null || u.getNombre().length() < 4 || u.getNombre().length() > 30) {
            throw new IllegalArgumentException("El nombre del usuario debe tener entre 4 y 30 caracteres");
        }

        // Validación del apellido paterno
        if (u.getApellidoPaterno() == null || u.getApellidoPaterno().length() < 4 || u.getApellidoPaterno().length() > 30) {
            throw new IllegalArgumentException("El apellido paterno del usuario debe tener entre 4 y 30 caracteres");
        }

        // Validación del apellido materno
        if (u.getApellidoMaterno() == null || u.getApellidoMaterno().length() < 4 || u.getApellidoMaterno().length() > 30) {
            throw new IllegalArgumentException("El apellido materno del usuario debe tener entre 4 y 30 caracteres");
        }

        // Validación del teléfono
        if (u.getTelefono() == null) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo");
        } else if (u.getTelefono().length() != 10) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 10 dígitos");
        } else if (!u.getTelefono().matches("\\d{10}")) {
            throw new IllegalArgumentException("El teléfono debe contener solo dígitos");
        }
        
        // Validación del rol
        if (u.getRol() == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        } else if (!u.getRol().equals("ALUMN") && !u.getRol().equals("BIBLI") && !u.getRol().equals("ADMIN")) {
            throw new IllegalArgumentException("El rol no corresponde a los existentes");
        }
        
        DAOUsuario daou = new DAOUsuario();
        int resultado = daou.insertarUsuario(u);

        return resultado;
    }
    
    public int modificarUsuario(Usuario u) throws SQLException, IOException, ClassNotFoundException {
        // Validación del nombre
        if (u.getNombre() == null || u.getNombre().length() < 4 || u.getNombre().length() > 30) {
            throw new IllegalArgumentException("El nombre del usuario debe tener entre 4 y 30 caracteres");
        }

        // Validación del apellido paterno
        if (u.getApellidoPaterno() == null || u.getApellidoPaterno().length() < 4 || u.getApellidoPaterno().length() > 30) {
            throw new IllegalArgumentException("El apellido paterno del usuario debe tener entre 4 y 30 caracteres");
        }

        // Validación del apellido materno
        if (u.getApellidoMaterno() == null || u.getApellidoMaterno().length() < 4 || u.getApellidoMaterno().length() > 30) {
            throw new IllegalArgumentException("El apellido materno del usuario debe tener entre 4 y 30 caracteres");
        }

        // Validación del teléfono
        if (u.getTelefono() == null) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo");
        } else if (u.getTelefono().length() != 10) {
            throw new IllegalArgumentException("El teléfono debe tener exactamente 10 dígitos");
        } else if (!u.getTelefono().matches("\\d{10}")) {
            throw new IllegalArgumentException("El teléfono debe contener solo dígitos");
        }
        
        // Validación del rol
        if (u.getRol() == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        } else if (!u.getRol().equals("ALUMN") && !u.getRol().equals("BIBLI") && !u.getRol().equals("ADMIN")) {
            throw new IllegalArgumentException("El rol no corresponde a los existentes");
        }
        
        DAOUsuario daou = new DAOUsuario();
        int resultado = daou.modificarUsuario(u);

        return resultado;
    }
    
    public void activarUsuario(Usuario u) throws ClassNotFoundException, SQLException {
        // Validación del ID del usuario
        if (u.getIdUsuario()<= 0) {
            throw new IllegalArgumentException("El usuario a reactivar no es valido");
        }

        DAOUsuario daou = new DAOUsuario();
        daou.activar(u);
    }
    
    public void eliminarUsuario(Usuario u) throws ClassNotFoundException, SQLException {
        // Validación del ID del usuario
        if (u.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El usuario a eliminar no es valido");
        }

        DAOUsuario daou = new DAOUsuario();
        daou.eliminar(u);
    }
}
