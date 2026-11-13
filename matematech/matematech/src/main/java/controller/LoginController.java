package controller;

import modelo.Usuario;
import repositorio.usuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private usuarioRepo usuarioRepository;

    @GetMapping({"/", "/login"})
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, 
                                @RequestParam String contrasena, 
                                Model model) {
        
        Usuario usuario = usuarioRepository.findByCorreo(correo);

        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            if ("Docente".equalsIgnoreCase(usuario.getRol())) {
                return "redirect:/dashboard";
            } else if ("Estudiante".equalsIgnoreCase(usuario.getRol())) {
                return "redirect:/dashboard";
            }
        }

        model.addAttribute("error", "Correo o contraseña incorrectos");
        return "login";
    }
}