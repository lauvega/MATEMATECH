package com.matematech.matematech.control;


import com.matematech.modelo.Pregunta;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/api/preguntas")
public class PreguntaController {

    private List<Pregunta> listaPreguntas = new ArrayList<>();
    private Queue<Pregunta> colaExamen = new LinkedList<>();

    @GetMapping
    public List<Pregunta> listar() {
        return listaPreguntas;
    }

    @GetMapping("/{id}")
    public Pregunta buscar(@PathVariable int id) {
        return listaPreguntas.stream()
                .filter(p -> p.getId_pregunta() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public String agregar(@RequestBody Pregunta pregunta) {
        if (buscar(pregunta.getId_pregunta()) != null) {
            return "Ya existe una pregunta con id " + pregunta.getId_pregunta();
        }
        listaPreguntas.add(pregunta);
        colaExamen.add(pregunta);
        return "Pregunta agregada correctamente a la lista y a la cola del examen";
    }

    @GetMapping("/siguiente")
    public Pregunta obtenerSiguientePregunta() {
        return colaExamen.poll();
    }

    @GetMapping("/ver-siguiente")
    public Pregunta verSiguientePregunta() {
        return colaExamen.peek();
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable int id, @RequestBody Pregunta nueva) {
        Pregunta existente = buscar(id);
        if (existente == null) {
            return "Pregunta no encontrada";
        }
        existente.setEnunciado(nueva.getEnunciado());
        existente.setExplicacion(nueva.getExplicacion());
        existente.setNivelDificultad(nueva.getNivelDificultad());
        existente.setCompetencia(nueva.getCompetencia());
        existente.setOpciones(nueva.getOpciones());
        return "Pregunta actualizada";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        Pregunta p = buscar(id);
        if (p == null) {
            return "Pregunta no encontrada";
        }
        listaPreguntas.remove(p);
        colaExamen.remove(p);
        return "Pregunta eliminada";
    }
}

