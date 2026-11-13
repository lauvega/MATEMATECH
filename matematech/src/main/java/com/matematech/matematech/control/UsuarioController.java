package com.matematech.matematech.control;



import com.matematech.modelo.Estudiante;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private List<Estudiante> estudiantes = new ArrayList<>();

    @GetMapping
    public List<Estudiante> listar() {
        return estudiantes;
    }

    @GetMapping("/{id}")
    public Estudiante buscar(@PathVariable int id) {
        return estudiantes.stream()
                .filter(e -> e.getId_usuario() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public String agregar(@RequestBody Estudiante estudiante) {
        if (buscar(estudiante.getId_usuario()) != null) {
            return "Ya existe un usuario con id " + estudiante.getId_usuario();
        }
        estudiantes.add(estudiante);
        return "Usuario agregado correctamente";
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable int id, @RequestBody Estudiante nuevo) {
        Estudiante existente = buscar(id);
        if (existente == null) {
            return "Usuario no encontrado";
        }
        existente.setNombre(nuevo.getNombre());
        existente.setCorreo(nuevo.getCorreo());
        existente.setContrasena(nuevo.getContrasena());
        existente.setRol(nuevo.getRol());
        existente.setGrado(nuevo.getGrado());
        return "Usuario actualizado";
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        Estudiante e = buscar(id);
        if (e == null) {
            return "Usuario no encontrado";
        }
        estudiantes.remove(e);
        return "Usuario eliminado";
    }
}