package org.utl.dsm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.utl.dsm.appservice.LibroExternoAppService;
import org.utl.dsm.cqrs.CQRSLibro;
import org.utl.dsm.dao.DAOLibro;
import org.utl.dsm.model.Libro;
import org.utl.dsm.viewmodel.LibroViewModel;

public class ControllerLibro {

    public int insertarLibro(Libro libro) throws IllegalArgumentException, SQLException {
        try {
            CQRSLibro cqrs = new CQRSLibro();
            return cqrs.insertarLibro(libro);
        } catch (IOException | ClassNotFoundException ex) {
            return -1;
        }
    }

    public int modificarLibro(Libro libro) throws IllegalArgumentException, SQLException {
        try {
            CQRSLibro cqrs = new CQRSLibro();
            return cqrs.modificarLibro(libro);
        } catch (IOException | ClassNotFoundException ex) {
            return -1;
        }
    }

    public List<Libro> getAllLibrosActivos() throws SQLException, ClassNotFoundException, IOException {
        DAOLibro daol = new DAOLibro();
        List<Libro> listaLibros = daol.getAllActivos();

        return listaLibros;
    }

    public List<Libro> getAllLibrosInactivos() throws SQLException, ClassNotFoundException, IOException {
        DAOLibro daol = new DAOLibro();
        List<Libro> listaLibros = daol.getAllInactivos();

        return listaLibros;
    }

    public List<LibroViewModel> getAllLibrosPublicos() throws SQLException, ClassNotFoundException, IOException {
        DAOLibro daol = new DAOLibro();
        List<Libro> libros = daol.getAllActivos();
        List<LibroViewModel> lista = new ArrayList<>();

        for (Libro libro : libros) {
            LibroViewModel modelo = new LibroViewModel(libro.getId_libro(),
                    libro.getNombre_libro(),
                    libro.getAutor(),
                    libro.getGenero(),
                    libro.getEstatus(),
                    libro.getArchivo_pdf(),
                    libro.getUniversidad()
            );
            lista.add(modelo);
        }
        return lista;
    }

    public List<LibroViewModel> getAllLibrosPublicosTodos() throws SQLException, ClassNotFoundException, IOException {
        List<LibroViewModel> listaPropia = getAllLibrosPublicos();
        
        // Instancia del servicio externo
        LibroExternoAppService leas = new LibroExternoAppService();

        // Obtener los libros externos de la API
        List<LibroViewModel> listaExterna = leas.getAll();

        listaPropia.addAll(listaExterna);

        return listaPropia; // Retornar la lista combinada o solo la lista externa
    }

    public void activarLibro(Libro l) throws ClassNotFoundException, SQLException {
        CQRSLibro cqrsl = new CQRSLibro();
        cqrsl.activarLibro(l);
    }

    public void eliminarLibro(Libro l) throws ClassNotFoundException, SQLException {
        CQRSLibro cqrsl = new CQRSLibro();
        cqrsl.eliminarLibro(l);
    }
}
