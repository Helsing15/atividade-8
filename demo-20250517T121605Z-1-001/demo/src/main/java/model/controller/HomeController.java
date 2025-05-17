package model.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/pacientes"})
    public String index() {
        return "pacientes"; // carrega pacientes.html
    }
}
