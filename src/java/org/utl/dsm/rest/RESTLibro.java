package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.utl.dsm.controller.ControllerLibro;
import org.utl.dsm.model.Libro;
import org.utl.dsm.viewmodel.LibroViewModel;

@Path("libro")
public class RESTLibro {

    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarLibro(@FormParam("l") @DefaultValue("") String libro) {
        Gson objGson = new Gson();
        Libro l = objGson.fromJson(libro, Libro.class);
        String out = "";
        ControllerLibro cl = new ControllerLibro();

        try {
            int resultado = cl.insertarLibro(l);
            if (resultado > 0) {
                out = "{\"result\":\"¡Libro registrado exitosamente!\"}";
            } else if (resultado == -1) {
                out = "{\"error\":\"¡Ha ocurrido un problema al registrar el libro!\"}";
            }
        } catch (IllegalArgumentException | SQLException e) {
            out = "{\"error\":\"¡"+e.getMessage()+"!\"}";
        }

        return Response.ok(out).build();
    }

    @Path("modificar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarLibro(@FormParam("l") @DefaultValue("") String libro) {
        Gson objGson = new Gson();
        Libro l = objGson.fromJson(libro, Libro.class);
        String out = "";
        ControllerLibro cl = new ControllerLibro();

        try {
            int resultado = cl.modificarLibro(l);
            if (resultado > 0) {
                out = "{\"result\":\"¡Libro registrado exitosamente!\"}";
            } else if (resultado == -1) {
                out = "{\"error\":\"¡Ha ocurrido un problema al registrar el libro!\"}";
            }
        } catch (IllegalArgumentException | SQLException e) {
            out = "{\"error\":\"¡"+e.getMessage()+"!\"}";
        }

        return Response.ok(out).build();
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLibros(@QueryParam("activo") @DefaultValue("true") boolean activo) {
        String out = "";
        try {
            ControllerLibro cl = new ControllerLibro();
            List<Libro> listaLibros = activo ? cl.getAllLibrosActivos() : cl.getAllLibrosInactivos();
            Gson objGson = new Gson();
            out = objGson.toJson(listaLibros);
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            out = "{\"error\":\"No se encontraron Libros Registrados\n"+ex.getMessage()+"\"}";
        }
        return Response.ok(out).build();
    }

    @Path("getAllPublicoTodo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLibrosPublicos() {
        String out = "";
        try {
            ControllerLibro cl = new ControllerLibro();
            List<LibroViewModel> listaLibros = cl.getAllLibrosPublicosTodos();
            Gson objGson = new Gson();
            out = objGson.toJson(listaLibros);
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            out = "{\"error\":\"No se encontraron Libros Registrados\n"+ex.getMessage()+"\"}";
        }
        return Response.ok(out).build();
    }
    
    @Path("getAllPublico")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPublico() {
        String out = "";
        try {
            ControllerLibro cl = new ControllerLibro();
            List<LibroViewModel> listaLibros = cl.getAllLibrosPublicos();
            Gson objGson = new Gson();
            out = objGson.toJson(listaLibros);
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            out = "{\"error\":\"No se encontraron Libros Registrados\n"+ex.getMessage()+"\"}";
        }
        return Response.ok(out).build();
    }

    @Path("eliminar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarLibro(@QueryParam("id_libro") @DefaultValue("0") String id_libro) {
        String out = "";
        try {
            Libro l = new Libro();
            l.setId_libro(Integer.parseInt(id_libro));
            ControllerLibro cl = new ControllerLibro();
            cl.eliminarLibro(l);
            out = "{\"result\":\"¡Libro eliminado correctamente!\"}";
        } catch (ClassNotFoundException ex) {
            out = "{\"error\":\"¡Libro no eliminado!\"}";
        } catch (IllegalArgumentException | SQLException ex) {
            out = "{\"error\":\"¡"+ex.getMessage()+"!\"}";
        }
        return Response.ok(out).build();
    }

    @Path("reactivar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response activarLibro(@QueryParam("id_libro") @DefaultValue("0") String id_libro) {
        String out = "";
        try {
            Libro l = new Libro();
            l.setId_libro(Integer.parseInt(id_libro));
            ControllerLibro cl = new ControllerLibro();
            cl.activarLibro(l);
            out = "{\"result\":\"¡Libro reactivado correctamente!\"}";
        } catch (ClassNotFoundException ex) {
            out = "{\"error\":\"¡Libro no reactivado!\"}";
        } catch (IllegalArgumentException | SQLException ex) {
            out = "{\"error\":\"¡"+ex.getMessage()+"!\"}";
        }
        return Response.ok(out).build();
    }
}
