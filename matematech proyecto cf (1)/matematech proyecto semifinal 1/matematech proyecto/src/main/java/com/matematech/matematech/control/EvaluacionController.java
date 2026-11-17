package com.matematech.matematech.control;

import com.matematech.matematech.modelo.Evaluacion;
import com.matematech.matematech.modelo.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EvaluacionController {

    @GetMapping("/evaluacion")
    public String mostrarEvaluacion(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || !"Estudiante".equalsIgnoreCase(usuario.getRol())) {
            session.invalidate();
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "login/evaluacion";
    }

    @ResponseBody
    @GetMapping("/api/evaluaciones/{id}/preguntas")
    public List<Object> obtenerPreguntasConOpciones(@PathVariable int id) {
        List<Object> listaPreguntas = new ArrayList<>();
        String sqlPregunta = "SELECT p.id_pregunta, p.enunciado, p.nivelDificultad, c.nombre as competencia " +
                "FROM PREGUNTA p " +
                "JOIN EVALUACION_PREGUNTA ep ON p.id_pregunta = ep.id_pregunta " +
                "JOIN COMPETENCIA c ON p.id_competencia = c.id_competencia " +
                "WHERE ep.id_evaluacion = ? ORDER BY ep.ordenPregunta";

        try (Connection con = UsuarioController.conectar();
             PreparedStatement pst = con.prepareStatement(sqlPregunta)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int idPregunta = rs.getInt("id_pregunta");

                List<Object> opciones = new ArrayList<>();
                String sqlOpcion = "SELECT letra, texto, esCorrecta FROM OPCION WHERE id_pregunta = ? ORDER BY letra";
                try (PreparedStatement pstOpt = con.prepareStatement(sqlOpcion)) {
                    pstOpt.setInt(1, idPregunta);
                    ResultSet rsOpt = pstOpt.executeQuery();
                    while (rsOpt.next()) {
                        Map<String, Object> optMap = new HashMap<>();
                        optMap.put("letra", rsOpt.getString("letra"));
                        optMap.put("texto", rsOpt.getString("texto"));
                        optMap.put("esCorrecta", rsOpt.getBoolean("esCorrecta"));
                        opciones.add(optMap);
                    }
                }

                Map<String, Object> pregMap = new HashMap<>();
                pregMap.put("id_pregunta", idPregunta);
                pregMap.put("enunciado", rs.getString("enunciado"));
                pregMap.put("competencia", rs.getString("competencia"));
                pregMap.put("opciones", opciones);

                listaPreguntas.add(pregMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPreguntas;
    }

    @ResponseBody
    @PostMapping("/api/evaluaciones/{id}/finalizar")
    public Map<String, Object> finalizarEvaluacion(@PathVariable int id, @RequestBody Map<String, String> respuestas, HttpSession session) {
        Map<String, Object> resultadoMap = new HashMap<>();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resultadoMap.put("error", "No autorizado");
            return resultadoMap;
        }

        int totalPreguntas = 0;
        int correctas = 0;

        String sqlPreguntas = "SELECT p.id_pregunta FROM PREGUNTA p " +
                "JOIN EVALUACION_PREGUNTA ep ON p.id_pregunta = ep.id_pregunta " +
                "WHERE ep.id_evaluacion = ? ORDER BY ep.ordenPregunta";

        try (Connection con = UsuarioController.conectar();
             PreparedStatement pst = con.prepareStatement(sqlPreguntas)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            int index = 0;
            while (rs.next()) {
                int idPreg = rs.getInt("id_pregunta");
                totalPreguntas++;

                String respuestaUsuario = respuestas.get(String.valueOf(index));

                String sqlOpt = "SELECT letra, esCorrecta FROM OPCION WHERE id_pregunta = ?";
                try (PreparedStatement pstOpt = con.prepareStatement(sqlOpt)) {
                    pstOpt.setInt(1, idPreg);
                    ResultSet rsOpt = pstOpt.executeQuery();

                    while (rsOpt.next()) {
                        String letra = rsOpt.getString("letra");
                        boolean esCorr = rsOpt.getBoolean("esCorrecta");

                        if (esCorr && letra.equals(respuestaUsuario)) {
                            correctas++;
                        }
                    }
                }
                index++;
            }

            double porcentaje = totalPreguntas > 0 ? ((double) correctas / totalPreguntas) * 100 : 0.0;

            String sqlInsertRes = "INSERT INTO RESULTADO (puntaje, porcentaje, fechaRealizacion, fechaHoraInicio, fechaHoraFin, id_estudiante, id_evaluacion) VALUES (?, ?, CURDATE(), NOW(), NOW(), ?, ?)";
            try (PreparedStatement pstRes = con.prepareStatement(sqlInsertRes)) {
                pstRes.setDouble(1, correctas);
                pstRes.setDouble(2, porcentaje);
                pstRes.setInt(3, usuario.getId_usuario());
                pstRes.setInt(4, id);
                pstRes.executeUpdate();
            }

            resultadoMap.put("correctas", correctas);
            resultadoMap.put("total", totalPreguntas);
            resultadoMap.put("porcentaje", porcentaje);

        } catch (SQLException e) {
            e.printStackTrace();
            resultadoMap.put("error", e.getMessage());
        }

        return resultadoMap;
    }

    @ResponseBody
    @GetMapping("/api/evaluaciones")
    public List<Evaluacion> listarApi() {
        List<Evaluacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM EVALUACION";

        try (Connection con = UsuarioController.conectar();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Evaluacion e = new Evaluacion();
                e.setId_evaluacion(rs.getInt("id_evaluacion"));
                e.setTitulo(rs.getString("titulo"));
                e.setDescripcion(rs.getString("descripcion"));
                e.setTiempoLimite(rs.getInt("tiempoLimite"));
                e.setFechaCreacion(rs.getDate("fechaCreacion"));
                lista.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}