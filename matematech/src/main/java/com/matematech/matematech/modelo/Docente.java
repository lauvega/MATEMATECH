package com.matematech.matematech.modelo;


public class Docente extends Usuario {
    private String asignatura;

    public Docente() {
        super();
    }

    public Docente(int id_usuario, String nombre, String correo, String contrasena, String rol, String asignatura) {
        super(id_usuario, nombre, correo, contrasena, rol);
        this.asignatura = asignatura;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
}