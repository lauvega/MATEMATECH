package com.matematech.matematech.control;

import com.matematech.matematech.modelo.Evaluacion;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private List<Evaluacion> evaluaciones = new ArrayList<>();

    @GetMapping
    public List<Evaluacion> listar() {
        return evaluaciones;
    }

    @GetMapping("/{id}")
    public Evaluacion buscar(@PathVariable int id) {
        return evaluaciones.stream()
                .filter(e -> e.getId_evaluacion() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public String agregar(@RequestBody Evaluacion evaluacion) {
        if (buscar(evaluacion.getId_evaluacion()) != null) {
            return "Ya existe una evaluacion con id " + evaluacion.getId_evaluacion();
        }
        evaluaciones.add(evaluacion);
        return "Evaluacion agregada correctamente";
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable int id, @RequestBody Evaluacion nueva) {
        Evaluacion existente = buscar(id);
        if (existente == null) {
            return "Evaluacion no encontrada";
        }
        existente.setTitulo(nueva.getTitulo());
        existente.setDescripcion(nueva.getDescripcion());
        existente.setTiempoLimite(nueva.getTiempoLimite());
        existente.setFechaCreacion(nueva.getFechaCreacion());
        return "Evaluacion actualizada";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        Evaluacion e = buscar(id);
        if (e == null) {
            return "Evaluacion no encontrada";
        }
        evaluaciones.remove(e);
        return "Evaluacion eliminada";
    }
}