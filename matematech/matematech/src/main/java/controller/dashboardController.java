package controller;

import repositorio.evaluacionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboardController {

    private final evaluacionRepo evaluacionRepository;

    public dashboardController(evaluacionRepo evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        model.addAttribute("evaluaciones", evaluacionRepository.findAll());
        return "dashboard";
    }
}