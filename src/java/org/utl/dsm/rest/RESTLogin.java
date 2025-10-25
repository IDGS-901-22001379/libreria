package org.utl.dsm.rest;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import org.utl.dsm.controller.ControllerLogin;
import org.utl.dsm.model.Usuario;

@Path("login")
public class RESTLogin {
    
    @Path("acceso")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("usuario") @DefaultValue("") String usuario, @FormParam("contrasenia") @DefaultValue("") String contrasenia) {
        String out = "";
        try {
            ControllerLogin cl = new ControllerLogin();
            Usuario usu = cl.login(usuario, contrasenia);
            out = "{\"exito\":\"¡CREDENCIALES CORRECTAS!\",\"rol\":\"" + usu.getRol() + "\",\"token\":\"" + usu.getToken() + "\"}";
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            out = "{\"error\":\"¡A ocurrido un error! "+ex.getMessage()+"\"}";
        } catch (IllegalArgumentException ex) {
            out = "{\"error\":\""+ex.getMessage()+"\"}";
        }
        return Response.ok(out).build(); 
    }
}