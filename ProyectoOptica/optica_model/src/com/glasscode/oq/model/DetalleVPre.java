/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.glasscode.oq.model;

import java.util.List;

/**
 *
 * @author Lenovo
 */
public class DetalleVPre {
    private Venta venta;
    private List<VentaPresupuesto> listaVPre;

    public DetalleVPre() {
    }

    public DetalleVPre(Venta venta, List<VentaPresupuesto> listaVPre) {
        this.venta = venta;
        this.listaVPre = listaVPre;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<VentaPresupuesto> getListaVPre() {
        return listaVPre;
    }

    public void setListaVPre(List<VentaPresupuesto> listaVPre) {
        this.listaVPre = listaVPre;
    }

    @Override
    public String toString() {
        String mensaje="";
        for (VentaPresupuesto listaPresupuesto : listaVPre) {
            mensaje += listaPresupuesto.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("DetalleVPre{");
        sb.append("venta=").append(venta);
        sb.append(", listaVPre=").append(listaVPre);
        sb.append('}');
        return sb.toString();
    }
    
    
}
