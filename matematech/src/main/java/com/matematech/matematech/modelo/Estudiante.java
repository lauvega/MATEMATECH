package com.matematech.matematech.modelo;

public class Estudiante extends Usuario {
    private String grado = "11";

    public Estudiante() {
        super();
    }

    public Estudiante(int id_usuario, String nombre, String correo, String contrasena, String rol, String grado) {
        super(id_usuario, nombre, correo, contrasena, rol);
        this.grado = grado;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
}