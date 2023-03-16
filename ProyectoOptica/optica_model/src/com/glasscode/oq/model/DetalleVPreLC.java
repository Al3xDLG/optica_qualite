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
public class DetalleVPreLC {
    private Venta venta;
    private List<VentaPresupuestoLC> ventaPresupuesto;

    public DetalleVPreLC() {
    }

    public DetalleVPreLC(Venta venta, List<VentaPresupuestoLC> listaVPre) {
        this.venta = venta;
        this.ventaPresupuesto = listaVPre;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<VentaPresupuestoLC> getVentaPresupuesto() {
        return ventaPresupuesto;
    }

    public void setVentaPresupuesto(List<VentaPresupuestoLC> ventaPresupuesto) {
        this.ventaPresupuesto = ventaPresupuesto;
    }

    @Override
    public String toString() {
        String mensaje="";
        for (VentaPresupuestoLC listaPresupuesto : ventaPresupuesto) {
            mensaje += listaPresupuesto.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("DetalleVPre{");
        sb.append("venta=").append(venta);
        sb.append(", listaVPre=").append(ventaPresupuesto);
        sb.append('}');
        return sb.toString();
    }
    
    
}
