package com.example.lab_c2.entidades;

public class Alquiler {
    private String idA;
    private String fechaInicio;
    private String fechaFin;
    private String tiempoAlquiler;
    private String precioAlquiler;
    private String nombreC;
    private String nombreV;

    public Alquiler(){

    }

    public Alquiler(String idA, String fechaInicio, String fechaFin, String tiempoAlquiler, String precioAlquiler, String nombreC, String nombreV){
        this.setIdV(idA);
        this.setFechaInicio(fechaInicio);
        this.setFechaFin(fechaFin);
        this.setTiempoAlquiler(tiempoAlquiler);
        this.setPrecioAlquiler(precioAlquiler);
        this.setNombreC(nombreC);
        this.setNombreV(nombreV);
    }


    public String getIdA() {
        return idA;
    }

    public void setIdV(String idA) {
        this.idA = idA;
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

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getNombreV() {
        return nombreV;
    }

    public void setNombreV(String nombreV) {
        this.nombreV = nombreV;
    }
}
