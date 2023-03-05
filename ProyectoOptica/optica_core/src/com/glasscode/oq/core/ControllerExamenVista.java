/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.glasscode.oq.core;

import com.glasscode.oq.bd.ConexionMySQL;
import com.glasscode.oq.model.Cliente;
import com.glasscode.oq.model.Empleado;
import com.glasscode.oq.model.ExamenVista;
import com.glasscode.oq.model.Graduacion;
import com.glasscode.oq.model.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ControllerExamenVista {
    public String armarConsultaSQL(String filtro) {
        // String = utlizar LIKE
        String sql = "SELECT * FROM v_examenvista_cliente ";
        String sqlWhere = "";
        // Revisamos que el filtro no sea nulo y no este vacío
        if (filtro != null && !filtro.trim().equals("")) {
            sqlWhere += "WHERE CAST(idCliente AS CHAR) = ?";
        }
        sql = sql + sqlWhere;

        return sql;
    }

    public List<ExamenVista> getAll(String filtro) throws Exception {
        //La consulta SQL a ejecutar:
        String sql = armarConsultaSQL(filtro);

        /*
        if (filtro.equals("") || filtro.equals("1")) {
            sql = "SELECT * FROM v_lentes_contacto WHERE estatus = 1";
        } else {
            sql = "SELECT * FROM v_lentes_contacto WHERE estatus = 0";
        }
         */
        ConexionMySQL cmsql = new ConexionMySQL();

        Connection conn = cmsql.open();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = null;

        List<ExamenVista> examenesVista = new ArrayList<>();

        System.out.println(sql);

        if (filtro != null && !filtro.trim().equals("")) {
            pstmt.setString(1, "%"+filtro+"%");
            pstmt.setString(2, "%"+filtro+"%");
            pstmt.setString(3, "%"+filtro+"%");
            pstmt.setString(4, "%"+filtro+"%");
            pstmt.setString(5, "%"+filtro+"%");
            pstmt.setString(6, "%"+filtro+"%");
            pstmt.setString(7, "%"+filtro+"%");
            pstmt.setString(8, "%"+filtro+"%");
            pstmt.setString(9, "%"+filtro+"%");
            pstmt.setString(10, "%"+filtro+"%");
            pstmt.setString(11, "%"+filtro+"%");
        }

        rs = pstmt.executeQuery();
        while (rs.next()) {
            examenesVista.add(fill(rs));
        }

        rs.close();
        pstmt.close();
        cmsql.close();

        return examenesVista;
    }

    public ExamenVista fill(ResultSet rs) throws Exception {
        ExamenVista ev = new ExamenVista();
        Empleado e = new Empleado();
        Cliente c = new Cliente();
        Graduacion g = new Graduacion();
        Persona p = new Persona();

        // Empleado
        e.setIdEmpleado(rs.getInt("idEmpleado"));
        e.setNumeroUnico(rs.getString("numeroUnicoEmpleado"));
        p.setNombre(rs.getString("nombreEmpleado"));
        p.setApellidoPaterno(rs.getString("apellidoPaternoEmpleado"));
        p.setApellidoMaterno(rs.getString("apellidoMaternoEmpleado"));
        e.setPersona(p);
        p = new Persona();
        // Lente de contacto
        c.setIdCliente(rs.getInt("idCliente"));
        c.setNumeroUnico(rs.getString("numeroUnicoCliente"));
        p.setNombre(rs.getString("nombreCliente"));
        p.setApellidoPaterno(rs.getString("apellidoPaternoCliente"));
        p.setApellidoMaterno(rs.getString("apellidoMaternoCliente"));
        c.setPersona(p);

        g.setEsferaod(rs.getDouble("esferaod"));
        g.setEsferaoi(rs.getDouble("esferaoi"));
        g.setCilindrood(rs.getInt("cilindrood"));
        g.setCilindrooi(rs.getInt("cilindrooi"));
        g.setEjeod(rs.getInt("ejeod"));
        g.setEjeoi(rs.getInt("ejeoi"));
        g.setDip(rs.getString("dip"));
        
        ev.setIdExamenVista(rs.getInt("idExamenVista"));
        ev.setClave(rs.getString("clave"));
        ev.setFecha(rs.getString("fecha"));
        ev.setGraduacion(g);
        ev.setCliente(c);
        ev.setEmpleado(e);
        
        return ev;
    }
}
