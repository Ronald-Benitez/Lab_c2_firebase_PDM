package com.example.lab_c2.entidades;

public class vehiculosCliente {
    private String nombreV;
    private String fechaInicio;
    private String fechaFin;
    private String tiempoAlquiler;
    private String precioAlquiler;

    public String getNombreV() {
        return nombreV;
    }

    public void setNombreV(String nombreV) {
        this.nombreV= nombreV;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTiempoAlquiler() {
        return tiempoAlquiler;
    }

    public void setTiempoAlquiler(String tiempoAlquiler) {
        this.tiempoAlquiler = tiempoAlquiler;
    }

    public String getPrecioAlquiler() {
        return precioAlquiler;
    }

    public void setPrecioAlquiler(String precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }
}
