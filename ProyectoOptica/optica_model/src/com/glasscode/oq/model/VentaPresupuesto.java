package com.glasscode.oq.model;

public class VentaPresupuesto {
    private Venta venta;
    private PresupuestoLentesdeContacto presupuesto;
    private int cantidad;
    private Double precioUnitario;
    private Double descuento;
    
    public VentaPresupuesto(){
        
    }

    public VentaPresupuesto(Venta venta, PresupuestoLentesdeContacto presupuesto, int cantidad, Double precioUnitario, Double descuento) {
        this.venta = venta;
        this.presupuesto = presupuesto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
    }
    
    

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public PresupuestoLentesdeContacto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(PresupuestoLentesdeContacto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
    
}
