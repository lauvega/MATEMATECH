package com.matematech.matematech.control;



import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matematech.matematech.modelo.Resultado;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoController {

    private List<Resultado> listaResultados = new ArrayList<>();
    private Stack<Resultado> historialReciente = new Stack<>();

    @GetMapping
    public List<Resultado> listar() {
        return listaResultados;
    }

    @GetMapping("/{id}")
    public Resultado buscar(@PathVariable int id) {
        return listaResultados.stream()
                .filter(r -> r.getId_resultado() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public String agregar(@RequestBody Resultado resultado) {
        if (buscar(resultado.getId_resultado()) != null) {
            return "Ya existe un resultado con id " + resultado.getId_resultado();
        }
        listaResultados.add(resultado);
        historialReciente.push(resultado);
        return "Resultado agregado a la lista e historial (Pila)";
    }

    @GetMapping("/ultimo")
    public Resultado verUltimoResultado() {
        if (!historialReciente.isEmpty()) {
            return historialReciente.peek();
        }
        return null;
    }

    @DeleteMapping("/deshacer-ultimo")
    public Resultado eliminarUltimoResultado() {
        if (!historialReciente.isEmpty()) {
            Resultado eliminado = historialReciente.pop();
            listaResultados.remove(eliminado);
            return eliminado;
        }
        return null;
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable int id, @RequestBody Resultado nuevo) {
        Resultado existente = buscar(id);
        if (existente == null) {
            return "Resultado no encontrado";
        }
        existente.setPuntaje(nuevo.getPuntaje());
        existente.setPorcentaje(nuevo.getPorcentaje());
        existente.setFechaRealizacion(nuevo.getFechaRealizacion());
        existente.setEstudiante(nuevo.getEstudiante());
        existente.setEvaluacion(nuevo.getEvaluacion());
        return "Resultado actualizado";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        Resultado r = buscar(id);
        if (r == null) {
            return "Resultado no encontrado";
        }
        listaResultados.remove(r);
        historialReciente.remove(r);
        return "Resultado eliminado";
    }
}