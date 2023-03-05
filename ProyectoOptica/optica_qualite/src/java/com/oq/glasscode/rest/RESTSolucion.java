/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oq.glasscode.rest;

import com.glasscode.oq.core.ControllerSolucion;
import com.glasscode.oq.model.Solucion;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author danya
 */
@Path("solucion")
public class RESTSolucion {

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro,
                           @QueryParam("showDeleted") @DefaultValue("false") boolean showDeleted) {
        String out = null;
        ControllerSolucion cs = null;
        
        List<Solucion> soluciones = null;
        try {
            cs = new ControllerSolucion();
            soluciones = cs.getAll(filtro, showDeleted);
            out = new Gson().toJson(soluciones);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosSolucion") @DefaultValue("") String datosSolucion) {

        String out = null;
        Gson gson = new Gson();
        Solucion sol = null;
        ControllerSolucion cs = new ControllerSolucion();
        try {
            sol = gson.fromJson(datosSolucion, Solucion.class);
            if (sol.getIdSolucion() == 0) {
                cs.insert(sol);
            } else {
                cs.update(sol);
            }
            out = gson.toJson(sol);
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
            out = """
                  {"exception" : "Formato JSON de Datos Incorrecto"}
                  """;
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"exception" : "%s"}
                  """;
            out = String.format(out, e);
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("id") @DefaultValue("-1") int id) {

        String out = null;
        Gson gson = new Gson();
        Solucion sol = null;
        ControllerSolucion cs = new ControllerSolucion();
        if (id != -1) {
            try {
                cs.delete(id);
                out = """
                  {"response" : "El producto ha sido eliminado."}
                  """;
            } catch (Exception e) {
                e.printStackTrace();
                out = """
                  {"exception" : "%s"}
                  """;
                out = String.format(out, e);
            }
        }
        else
        {
            out = """
                  {"response" : "No se ha podido realizar la eliminacion : id no se mando correctamente"}
                  """;
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
}