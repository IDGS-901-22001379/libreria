package org.utl.dsm.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import org.utl.dsm.model.Usuario;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.utl.dsm.controller.ControllerUsuario;

@Path("usuario")
public class RESTUsuario {

    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertarUsuario(@FormParam("u") @DefaultValue("") String usuario) {
        Gson objGson = new Gson();
        Usuario u = objGson.fromJson(usuario, Usuario.class);
        String out = "";
        ControllerUsuario cu = new ControllerUsuario();

        try {
            int resultado = cu.insertarUsuario(u);
            if (resultado > 0) {
                out = "{\"result\":\"¡Usuario registrado exitosamente!\"}";
            } else if (resultado == -1) {
                out = "{\"error\":\"¡Ha ocurrido un problema al registrar el usuario!\"}";
            }
        } catch (IllegalArgumentException | SQLException e) {
            out = "{\"error\":\"¡"+e.getMessage()+"!\"}";
        }

        return Response.ok(out).build();
    }
    
    @Path("modificar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarUsuario(@FormParam("u") @DefaultValue("") String usuario) {
        Gson objGson = new Gson();
        Usuario u = objGson.fromJson(usuario, Usuario.class);
        String out = "";
        ControllerUsuario cu = new ControllerUsuario();

        try {
            int resultado = cu.modificarUsuario(u);
            if (resultado > 0) {
                out = "{\"result\":\"¡Usuario modificado exitosamente!\"}";
            } else if (resultado == -1) {
                out = "{\"error\":\"¡Ha ocurrido un problema al registrar el usuario!\"}";
            }
        } catch (IllegalArgumentException | SQLException e) {
            out = "{\"error\":\"¡"+e.getMessage()+"!\"}";
        }

        return Response.ok(out).build();
    }
    
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsuarios(@QueryParam("activo") @DefaultValue("true") boolean activo) {
        String out = "";
        try {
            ControllerUsuario cu = new ControllerUsuario();
            List<Usuario> listaUsuarios = activo ? cu.getAllUsuariosActivos() : cu.getAllUsuariosInactivos();
            Gson objGson = new Gson();
            out = objGson.toJson(listaUsuarios);
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            out = "{\"error\":\"No se encontraron Usuarios Registrados\n"+ex.getMessage()+"\"}";
        }
        return Response.ok(out).build();
    }
    
    @Path("eliminar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarUsuario(@QueryParam("id_usuario") @DefaultValue("0") String id_usuario) {
        String out = "";
        try {
            Usuario u = new Usuario();
            u.setIdUsuario(Integer.parseInt(id_usuario));
            ControllerUsuario cu = new ControllerUsuario();
            cu.eliminarUsuario(u);
            out = "{\"result\":\"¡Usuario eliminado correctamente!\"}";
        } catch (ClassNotFoundException ex) {
            out = "{\"error\":\"¡Usuario no eliminado!\n"+ex.getMessage()+"\"}";
        } catch (IllegalArgumentException | SQLException ex) {
            out = "{\"error\":\"¡"+ex.getMessage()+"!\"}";
        }
        return Response.ok(out).build();
    }

    @Path("reactivar")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response activarUsuario(@QueryParam("id_usuario") @DefaultValue("0") String id_usuario) {
        String out = "";
        try {
            Usuario u = new Usuario();
            u.setIdUsuario(Integer.parseInt(id_usuario));
            ControllerUsuario cu = new ControllerUsuario();
            cu.activarUsuario(u);
            out = "{\"result\":\"¡Usuario reactivado correctamente!\"}";
        } catch (ClassNotFoundException ex) {
            out = "{\"error\":\"¡Usuario no reactivado!\n"+ex.getMessage()+"\"}";
        } catch (IllegalArgumentException | SQLException ex) {
            out = "{\"error\":\"¡"+ex.getMessage()+"!\"}";
        }
        return Response.ok(out).build();
    }
}
