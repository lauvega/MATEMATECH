package com.matematech.matematech.modelo;

public class Competencia {
    private int id_competencia;
    private String nombre;
    private String componente;

    public Competencia() {
    }

    public Competencia(int id_competencia, String nombre, String componente) {
        this.id_competencia = id_competencia;
        this.nombre = nombre;
        this.componente = componente;
    }

    public int getId_competencia() {
        return id_competencia;
    }

    public void setId_competencia(int id_competencia) {
        this.id_competencia = id_competencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }
}