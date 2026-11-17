package com.matematech.matematech.control;

import com.matematech.matematech.modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_evaluaciones?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Onepiece"; // Tu contraseña real de MySQL

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Usuario buscarPorCorreoYContrasena(String correo, String contrasenaPlana) {
        Usuario usuario = null;
        String sql = "SELECT * FROM USUARIO WHERE correo = ?";

        try (Connection con = conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, correo);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String contrasenaHash = rs.getString("contrasena");

                // Verificamos la contraseña plana ingresada contra el hash de BCrypt en la BD
                if (passwordEncoder.matches(contrasenaPlana, contrasenaHash)) {
                    usuario = new Usuario();
                    usuario.setId_usuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContrasena(contrasenaHash);
                    usuario.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public static boolean guardar(Usuario usuario) {
        String sqlUsuario = "INSERT INTO USUARIO (nombre, correo, contrasena, rol) VALUES (?, ?, ?, ?)";
        Connection con = null;

        try {
            con = conectar();
            if (con == null) return false;

            // Encriptamos la contraseña con BCrypt antes de guardarla
            String passwordEncrip = passwordEncoder.encode(usuario.getContrasena());

            try (PreparedStatement pst = con.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, usuario.getNombre());
                pst.setString(2, usuario.getCorreo());
                pst.setString(3, passwordEncrip);
                pst.setString(4, usuario.getRol());

                int filas = pst.executeUpdate();
                if (filas > 0) {
                    ResultSet rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);

                        // Inserción segura en tabla dependiente según el rol
                        if ("Docente".equalsIgnoreCase(usuario.getRol())) {
                            String sqlDocente = "INSERT INTO DOCENTE (id_usuario, asignatura) VALUES (?, ?)";
                            try (PreparedStatement pstDoc = con.prepareStatement(sqlDocente)) {
                                pstDoc.setInt(1, idGenerado);
                                pstDoc.setString(2, "General");
                                pstDoc.executeUpdate();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            String sqlEstudiante = "INSERT INTO ESTUDIANTE (id_usuario, grado) VALUES (?, ?)";
                            try (PreparedStatement pstEst = con.prepareStatement(sqlEstudiante)) {
                                pstEst.setInt(1, idGenerado);
                                pstEst.setString(2, "11");
                                pstEst.executeUpdate();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) { // Cambiado genéricamente para evitar el error de compilación
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception exClose) {
                exClose.printStackTrace();
            }
        }
    }

    public static List<Usuario> obtenerEstudiantes() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT U.* FROM USUARIO U JOIN ESTUDIANTE E ON U.id_usuario = E.id_usuario";

        try (Connection con = conectar(); PreparedStatement pst = con.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}