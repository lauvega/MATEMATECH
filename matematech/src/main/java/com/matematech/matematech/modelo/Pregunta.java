package com.matematech.matematech.modelo;

import java.util.ArrayList;
import java.util.List;

public class Pregunta {
    private int id_pregunta;
    private String enunciado;
    private String explicacion;
    private String nivelDificultad;
    private Competencia competencia;
    private List<Opcion> opciones;

    public Pregunta() {
        this.opciones = new ArrayList<>();
    }

    public Pregunta(int id_pregunta, String enunciado, String explicacion, String nivelDificultad, Competencia competencia) {
        this.id_pregunta = id_pregunta;
        this.enunciado = enunciado;
        this.explicacion = explicacion;
        this.nivelDificultad = nivelDificultad;
        this.competencia = competencia;
        this.opciones = new ArrayList<>();
    }

    public int getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(int id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    public String getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(String nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public Competencia getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
    }

    public List<Opcion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }
}
