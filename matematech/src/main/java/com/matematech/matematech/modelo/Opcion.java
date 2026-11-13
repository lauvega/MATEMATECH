package com.matematech.matematech.modelo;


public class Opcion {
    private int id_opcion;
    private String letra;
    private String texto;
    private boolean esCorrecta;

    public Opcion() {
    }

    public Opcion(int id_opcion, String letra, String texto, boolean esCorrecta) {
        this.id_opcion = id_opcion;
        this.letra = letra;
        this.texto = texto;
        this.esCorrecta = esCorrecta;
    }

    public int getId_opcion() {
        return id_opcion;
    }

    public void setId_opcion(int id_opcion) {
        this.id_opcion = id_opcion;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }
}