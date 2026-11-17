package com.matematech.matematech.modelo;

import java.util.Date;

public class Resultado {
    private int id_resultado;
    private Double puntaje;
    private Double porcentaje;
    private Date fechaRealizacion;
    private Estudiante estudiante;
    private Evaluacion evaluacion;

    public Resultado() {
    }

    public Resultado(int id_resultado, Double puntaje, Double porcentaje, Date fechaRealizacion, Estudiante estudiante, Evaluacion evaluacion) {
        this.id_resultado = id_resultado;
        this.puntaje = puntaje;
        this.porcentaje = porcentaje;
        this.fechaRealizacion = fechaRealizacion;
        this.estudiante = estudiante;
        this.evaluacion = evaluacion;
    }

    public int getId_resultado() {
        return id_resultado;
    }

    public void setId_resultado(int id_resultado) {
        this.id_resultado = id_resultado;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }
}