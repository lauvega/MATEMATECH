package com.matematech.matematech.control;

import com.matematech.matematech.modelo.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class HomeController {

    @GetMapping({"/", "/login"})
    public String mostrarLogin() {
        return "login/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String contrasena,
                                HttpSession session,
                                Model model) {

        Usuario encontrado = UsuarioController.buscarPorCorreoYContrasena(correo, contrasena);

        if (encontrado != null) {
            session.setAttribute("usuario", encontrado);

            if ("Docente".equalsIgnoreCase(encontrado.getRol())) {
                return "redirect:/docente/dashboard";
            } else {
                return "redirect:/dashboard";
            }
        }

        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "login/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(Usuario usuario,
                                   @RequestParam(required = false) String codigoAdmin,
                                   Model model) {

        if ("DOCENTE".equalsIgnoreCase(usuario.getRol())) {
            usuario.setRol("Docente");
            if (codigoAdmin == null || !codigoAdmin.equals("ADMIN*")) {
                model.addAttribute("error", "Código de autorización docente incorrecto.");
                model.addAttribute("usuario", usuario);
                return "login/registro";
            }
        } else {
            usuario.setRol("Estudiante");
        }

        boolean exito = UsuarioController.guardar(usuario);
        if (!exito) {
            model.addAttribute("error", "El correo ya se encuentra registrado o hubo un error en la base de datos.");
            model.addAttribute("usuario", usuario);
            return "login/registro";
        }

        return "redirect:/login?registrado=true";
    }

    @GetMapping("/dashboard")
    public String mostrarDashboardEstudiante(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !"Estudiante".equalsIgnoreCase(usuario.getRol())) {
            session.invalidate();
            return "redirect:/login";
        }

        boolean yaHizoSimulacro = false;
        double porcentajeObtenido = 0.0;
        int idUsuario = obtenerIdUsuario(usuario);

        if (idUsuario > 0) {
            String sql = "SELECT porcentaje FROM resultado WHERE id_estudiante = ? ORDER BY id_resultado DESC LIMIT 1";

            try (Connection con = UsuarioController.conectar();
                 PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, idUsuario);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    yaHizoSimulacro = true;
                    porcentajeObtenido = rs.getDouble("porcentaje");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("yaHizoSimulacro", yaHizoSimulacro);
        model.addAttribute("porcentajeObtenido", porcentajeObtenido);

        return "login/dashboard";
    }

    private int obtenerIdUsuario(Usuario usuario) {
        if (usuario == null) {
            return -1;
        }

        String[] nombresMetodos = {"getId", "getIdUsuario", "getIdEstudiante", "getUsuarioId", "getUserId"};
        for (String nombreMetodo : nombresMetodos) {
            try {
                Object valor = Usuario.class.getMethod(nombreMetodo).invoke(usuario);
                if (valor instanceof Number) {
                    return ((Number) valor).intValue();
                }
            } catch (ReflectiveOperationException ignored) {
            }
        }

        String[] nombresCampos = {"id", "idUsuario", "usuarioId", "userId"};
        for (String nombreCampo : nombresCampos) {
            try {
                var campo = Usuario.class.getDeclaredField(nombreCampo);
                campo.setAccessible(true);
                Object valor = campo.get(usuario);
                if (valor instanceof Number) {
                    return ((Number) valor).intValue();
                }
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        }

        return -1;
    }

    @GetMapping("/docente/dashboard")
    public String mostrarDashboardDocente(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !"Docente".equalsIgnoreCase(usuario.getRol())) {
            session.invalidate();
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("estudiantes", UsuarioController.obtenerEstudiantes());
        return "login/docente";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}