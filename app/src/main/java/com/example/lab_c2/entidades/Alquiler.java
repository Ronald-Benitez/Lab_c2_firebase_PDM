package com.example.lab_c2.entidades;

public class Alquiler {
    private int idA;
    private int idC;
    private int idV;
    private String fecchaInicio;
    private String fecchaFin;
    private String tiempoAlquiler;
    private String precioAlquiler;

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public int getIdV() {
        return idV;
    }

    public void setIdV(int idV) {
        this.idV = idV;
    }

    public String getFecchaInicio() {
        return fecchaInicio;
    }

    public void setFecchaInicio(String fecchaInicio) {
        this.fecchaInicio = fecchaInicio;
    }

    public String getFecchaFin() {
        return fecchaFin;
    }

    public void setFecchaFin(String fecchaFin) {
        this.fecchaFin = fecchaFin;
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
