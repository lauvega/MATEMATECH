package com.matematech.matematech.modelo;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evaluacion {
    private int id_evaluacion;
    private String titulo;
    private String descripcion;
    private int tiempoLimite;
    private Date fechaCreacion;
    private List<Pregunta> preguntas;

    public Evaluacion() {
        this.preguntas = new ArrayList<>();
    }

    public Evaluacion(int id_evaluacion, String titulo, String descripcion, int tiempoLimite, Date fechaCreacion) {
        this.id_evaluacion = id_evaluacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tiempoLimite = tiempoLimite;
        this.fechaCreacion = fechaCreacion;
        this.preguntas = new ArrayList<>();
    }

    public int getId_evaluacion() {
        return id_evaluacion;
    }

    public void setId_evaluacion(int id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTiempoLimite() {
        return tiempoLimite;
    }

    public void setTiempoLimite(int tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
