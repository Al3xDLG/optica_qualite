/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.glasscode.oq.core;

import com.glasscode.oq.bd.ConexionMySQL;
import com.glasscode.oq.model.DetalleVPre;
import com.glasscode.oq.model.VentaPresupuesto;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ControllerVentasLC {
    public boolean generarVentaLC(DetalleVPre dvpr)
    {
        boolean r = false;
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        ResultSet rs = null;
        Statement stmt = null;
        
        try {
            String query1 = "INSERT INTO venta (idEmpleado, clave) VALUES ("
                    + dvpr.getVenta().getEmpleado().getIdEmpleado()+",'"
                    + dvpr.getVenta().getClave()+"')";
            
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.execute(query1);
            
            String query2 = "SELECT LAST_INSERT_ID()";
            rs = stmt.executeQuery(query2);
            
            if (rs.next()) {
                dvpr.getVenta().setIdVenta(rs.getInt(1));
                
            }
            
            for (VentaPresupuesto VentaPre : dvpr.getListaVPre()) {
                String query3 = "INSERT INTO venta_preuspuesto (idVenta, idPresupuesto, cantidad, precioUnitario, descuento) VALUES("
                        + VentaPre.getVenta().getIdVenta() + ","
                        + VentaPre.getPresupuesto().getIdPresupuesto() + ","
                        + VentaPre.getCantidad() +","
                        + VentaPre.getPrecioUnitario() +","
                        + VentaPre.getDescuento() +")";
                stmt.execute(query3);
            }
            conn.commit();
            conn.setAutoCommit(true);
            rs.close();
            stmt.close();
            conn.close();
            r = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();

                rs.close();
                stmt.close();
                conn.close();
                connMySQL.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return r;
    }
}
