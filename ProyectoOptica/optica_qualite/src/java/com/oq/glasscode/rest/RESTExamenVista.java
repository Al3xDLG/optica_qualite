/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oq.glasscode.rest;

import com.glasscode.oq.core.ControllerExamenVista;
import com.glasscode.oq.core.ControllerLenteContacto;
import com.glasscode.oq.model.ExamenVista;
import com.glasscode.oq.model.LenteContacto;
import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author Lenovo
 */
@Path("examenvista")
public class RESTExamenVista {

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {
        String out = null;
        ControllerExamenVista cev = null;

        List<ExamenVista> lev = null;
        try {
            cev = new ControllerExamenVista();
            lev = cev.getAll(filtro);
            out = new Gson().toJson(lev);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"exception":"Error interno del servidor."}
                  """;
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
}
