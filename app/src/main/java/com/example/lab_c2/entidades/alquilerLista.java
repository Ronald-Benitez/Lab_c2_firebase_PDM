package com.example.lab_c2.entidades;

public class alquilerLista {
    private String idA;
    private String nombreV;
    private String nombreC;

    public alquilerLista(String idA, String nombreV, String nombreC) {
        this.idA = idA;
        this.nombreV = nombreV;
        this.nombreC = nombreC;
    }

    public alquilerLista(){

    }

    public String getIdA() {
        return idA;
    }

    public void setIdA(String idAlquiler) {
        this.idA = idAlquiler;
    }

    public String getNombreV() {
        return nombreV;
    }

    public void setNombreV(String nombreV) {
        this.nombreV = nombreV;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }
}
