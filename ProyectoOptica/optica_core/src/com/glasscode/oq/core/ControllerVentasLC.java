/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.glasscode.oq.core;

import com.glasscode.oq.bd.ConexionMySQL;
import com.glasscode.oq.model.DetalleVPre;
import com.glasscode.oq.model.PresupuestoLentesdeContacto;
import com.glasscode.oq.model.VentaPresupuesto;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ControllerVentasLC {

    public boolean generarVentaLC(DetalleVPre dvpr) {
        boolean r = false;
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            String query1 = "INSERT INTO venta (idEmpleado, clave) VALUES ("
                    + dvpr.getVenta().getEmpleado().getIdEmpleado() + ",'"
                    + dvpr.getVenta().getClave() + "')";
            stmt.execute(query1);

            String query2 = "SELECT LAST_INSERT_ID()";
            rs = stmt.executeQuery(query2);

            if (rs.next()) {
                dvpr.getVenta().setIdVenta(rs.getInt(1));

            }

            for (VentaPresupuesto VentaPre : dvpr.getListaVPre()) {
                String query3 = "INSERT INTO venta_preuspuesto (idVenta, idPresupuesto, cantidad, precioUnitario, descuento) VALUES("
                        + VentaPre.getVenta().getIdVenta() + ","
                        + VentaPre.getPresupuestoLC().getPresupuesto().getIdPresupuesto()+ ","
                        + VentaPre.getCantidad() + ","
                        + VentaPre.getPrecioUnitario() + ","
                        + VentaPre.getDescuento() + ")";
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
    
    public boolean generarVentaLCt(DetalleVPre dvpr){
        boolean r = false;
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        ResultSet rs = null;
        Statement stmt = null;
        
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            String query1 = "INSERT INTO venta (idEmpleado, clave) VALUES ("
                    + dvpr.getVenta().getEmpleado().getIdEmpleado() + ",'"
                    + dvpr.getVenta().getClave() + "')";
            stmt.execute(query1);

            String query2 = "SELECT LAST_INSERT_ID()";
            rs = stmt.executeQuery(query2);

            if (rs.next()) {
                dvpr.getVenta().setIdVenta(rs.getInt(1));

            }

            for (VentaPresupuesto VentaPre : dvpr.getListaVPre()) {
                String query3 = "INSERT INTO presupuesto (idExamenVista, clave) VALUES("
                        + VentaPre.getPresupuestoLC().getPresupuesto().getIdExamenVista()+ ","
                        + VentaPre.getVenta().getClave()+ ","
                        + VentaPre.getCantidad() + ")";
                stmt.execute(query3);
            }
            conn.commit();
            conn.setAutoCommit(true);
            rs.close();
            stmt.close();
            conn.close();
            r = true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();

                rs.close();
                stmt.close();
                conn.close();
                connMySQL.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return r;
    }

    public void generarPresupuestoLC(PresupuestoLentesdeContacto plc) {
        boolean r = false;
        //Con este objeto nos vamos a conectar a la Base de Datos:
        ConexionMySQL connMySQL = new ConexionMySQL();

        //Abrimos la conexión con la Base de Datos:
        Connection conn = connMySQL.open();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            String queryPresupuestoLC = "INSERT INTO presupuesto_lentescontacto "
                    + "(idExamenVista, idLenteContacto, clave) "
                    + "VALUES ("
                    + plc.getExamenVista().getIdExamenVista() + ", "
                    + plc.getLenteContacto().getIdLenteContacto() + ", "
                    + plc.getClave() + ")";
            stmt.execute(queryPresupuestoLC);
            String queryLastInsertIDPresupuesto = "SELECT_LAST_INSERT_ID()";
            rs = stmt.executeQuery(queryLastInsertIDPresupuesto);
            if (rs.next()) {
                plc.setIdPrespuestoLentesContacto(rs.getInt(1));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(ControllerVentasLC.class.getName()).log(Level.SEVERE, null, e);
            try {
                rs.close();
                stmt.close();
                conn.close();
                connMySQL.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
//        public boolean generarVentaLC1(DetalleVPre dvp) {
//        boolean r = false;
//        ConexionMySQL connMySQL = new ConexionMySQL();
//        Connection conn = connMySQL.open();
//        Statement stmnt = null;
//        ResultSet rs = null;
//        try {
//            conn.setAutoCommit(false);
//            stmnt = conn.createStatement();
//// Se inserta la venta
//            String query1 = "INSERT INTO venta (idEmpleado, clave) "
//                    + "VALUES (" + dvp.getVenta().getEmpleado().getIdEmpleado()
//                    + ',' + dvp.getVenta().getClave() + ");";
//            stmnt.execute(query1);
//            // Se obtiene el id de la venta que se ha insertado
//            String query2 = "SELECT LAST_INSERT_ID()";
//            rs = stmnt.executeQuery(query2);
//            if (rs.next()) {
//                dvp.getVenta().setIdVenta(rs.getInt(1));
//            }
//            for (int i = 0; i < dvp.getVentaPresupuesto().size(); i++) {
////Se inserta el presupuesto
//                String query3 = "INSERT INTO presupuesto"
//                        + "(idExamenVista, clave)"
//                        + "VALUES (" + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getExamenVista().getIdExamenVista()
//                        + "," + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().getClave() + " );";
//                stmnt.execute(query3);
//                //Se obtiene el id del presupuesto generado
//                rs = stmnt.executeQuery(query2);
//                if (rs.next()) {
//                    dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().setIdPresupuesto(rs.getInt(1));
//                }
////Se inserta en presupuesto_lentescontacto                
//                String query4 = "INSERT INTO presupuesto_lentescontacto"
//                        + "(idExamenVista, idLenteContacto, clave)"
//                        + "VALUES (" + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getExamenVista().getIdExamenVista() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getLenteContacto().getIdLenteContacto() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getClave() + ");";
//                stmnt.execute(query4);
//                //Se obtiene el id del presupuesto_lentescontacto generado
//                rs = stmnt.executeQuery(query2);
//                if (rs.next()) {
//                    dvp.getVentaPresupuesto().get(i).getPresupuestoLC().setIdPresupuestoLC(rs.getInt(1));
//                }//Se insera en venta_presupuesto la relación entre la venta y el presupuesto
//                String query5 = "INSERT INTO venta_presupuesto "
//                        + "(idVenta, idPresupuesto, cantidad, precioUnitario, descuento) "
//                        + "VALUES ("
//                        + dvp.getVenta().getIdVenta() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPresupuestoLC().getPresupuesto().getIdPresupuesto() + ","
//                        + dvp.getVentaPresupuesto().get(i).getCantidad() + ","
//                        + dvp.getVentaPresupuesto().get(i).getPrecioUnitario() + ","
//                        + dvp.getVentaPresupuesto().get(i).getDescuento() + ");";
//                stmnt.execute(query5);
//            }
//            conn.commit();
//            conn.setAutoCommit(true);
//            stmnt.close();
//            conn.close();
//            r = true;
//        } catch (SQLException ex) {
//            
// Logger.getLogger(ControladorVentaLC.class.getName()).log(Level.SEVERE, null, ex);
//            try {
//                conn.rollback();
//                conn.setAutoCommit(true);
//                conn.close();
//                r = false;
//            } catch (SQLException ex1) {
//                r = false;
//            }
//        }
//        return r;
//    }
    
    public void generarIDPresupuestoLC(DetalleVPre dvp)
    {
        for (VentaPresupuesto vp : dvp.getListaVPre()) {
            generarPresupuestoLC(vp.getPresupuestoLC());
        }
    }
}
