package model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import model.Paciente;
import model.repository.PacienteRepository;


@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteRepository.findAll());
        return "lista"; // ainda vamos criar esse HTML depois
    }

    // FORM CADASTRO
    @GetMapping("/novo")
    public String novoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "form"; // esse HTML também virá depois
    }

    // SALVAR (Cadastro ou edição)
    @PostMapping("/salvar")
    public String salvarPaciente(@ModelAttribute Paciente paciente) {
        pacienteRepository.save(paciente);
        return "redirect:/pacientes";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editarPaciente(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("paciente", paciente);
        return "form";
    }
}
