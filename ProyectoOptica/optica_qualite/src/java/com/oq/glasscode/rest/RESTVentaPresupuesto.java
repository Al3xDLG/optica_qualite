/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oq.glasscode.rest;

import com.glasscode.oq.core.ControllerLogin;
import com.glasscode.oq.core.ControllerVentaPresupuesto;
import com.glasscode.oq.model.DetalleVPreLC;
import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Lenovo
 */
@Path("ventaPresupuesto")
public class RESTVentaPresupuesto {
    @POST
    @Path("createVentaPLC")
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearVentaP(@FormParam ("datosVPLC") @DefaultValue("") String datosVPLC,
                                @FormParam ("token") @DefaultValue("") String token)
    {
        String out = null;
        DetalleVPreLC dvp = null;
        Gson gson = new Gson();
        ControllerLogin cl = null;
        ControllerVentaPresupuesto cvp = null;
        boolean realizado;
        
        try {
            cl = new ControllerLogin();
            if (cl.validarToken(token)) {
                cvp = new ControllerVentaPresupuesto();
                dvp = gson.fromJson(datosVPLC, DetalleVPreLC.class);
                realizado = cvp.generarVentaLC(dvp);
                if (realizado) {
                    out = """
                          {"response": "Se ha realizado la venta"}
                          """;
                }else
                {
                    out = """
                          {"error": "Hubo un error"}
                          """;
                }
            }
            else
            {
                out = """
                      {"errorsec": "Error al validar el token"}
                      """;
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
