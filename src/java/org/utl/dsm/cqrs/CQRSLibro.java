package org.utl.dsm.cqrs;

import org.utl.dsm.model.Libro;
import java.sql.SQLException;
import java.io.IOException;
import org.utl.dsm.dao.DAOLibro;

public class CQRSLibro {

    public int insertarLibro(Libro l) throws SQLException, IOException, ClassNotFoundException {
        // Validación del nombre
        if (l.getNombre_libro() == null || l.getNombre_libro().length() < 5 || l.getNombre_libro().length() > 100) {
            throw new IllegalArgumentException("El nombre del libro debe tener entre 5 y 100 caracteres.");
        }

        // Validación de la categoría
        if (l.getGenero() == null || l.getGenero().length() < 5 || l.getGenero().length() > 30) {
            throw new IllegalArgumentException("La categoría debe tener entre 5 y 30 caracteres.");
        }

        DAOLibro daol = new DAOLibro();
        int resultado = daol.insertarLibro(l);

        return resultado;
    }

    public int modificarLibro(Libro l) throws SQLException, IOException, ClassNotFoundException {
        // Validación del nombre
        if (l.getNombre_libro() == null || l.getNombre_libro().length() < 5 || l.getNombre_libro().length() > 100) {
            throw new IllegalArgumentException("El nombre del libro debe tener entre 5 y 100 caracteres.");
        }

        // Validación de la categoría
        if (l.getGenero() == null || l.getGenero().length() < 5 || l.getGenero().length() > 30) {
            throw new IllegalArgumentException("La categoría debe tener entre 5 y 30 caracteres.");
        }

        DAOLibro daol = new DAOLibro();
        int resultado = daol.modificarLibro(l);

        return resultado;
    }
    
    public void activarLibro(Libro l) throws ClassNotFoundException, SQLException {
        // Validación del ID del libro
        if (l.getId_libro() <= 0) {
            throw new IllegalArgumentException("El libro a reactivar no es valido");
        }

        DAOLibro daol = new DAOLibro();
        daol.activar(l);
    }
    
    public void eliminarLibro(Libro l) throws ClassNotFoundException, SQLException {
        // Validación del ID del libro
        if (l.getId_libro() <= 0) {
            throw new IllegalArgumentException("El libro a eliminar no es valido");
        }

        DAOLibro daol = new DAOLibro();
        daol.eliminar(l);
    }
}
