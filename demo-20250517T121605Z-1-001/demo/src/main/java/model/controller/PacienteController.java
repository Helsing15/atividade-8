package model.controller;

import model.Paciente;
import model.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    // LISTAR TODOS (GET)
    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> pacientes = repository.findAll();
        return ResponseEntity.ok(pacientes); // 200 OK
    }

    // BUSCAR POR ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = repository.findById(id);
        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get()); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
        }
    }

    // CRIAR NOVO (POST)
    @PostMapping
    public ResponseEntity<Paciente> salvar(@RequestBody Paciente paciente) {
        Paciente novoPaciente = repository.save(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente); // 201 Created
    }

    // ATUALIZAR EXISTENTE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody Paciente pacienteAtualizado) {
        Optional<Paciente> pacienteExistente = repository.findById(id);

        if (pacienteExistente.isPresent()) {
            Paciente paciente = pacienteExistente.get();
            paciente.setNome(pacienteAtualizado.getNome());
            paciente.setCpf(pacienteAtualizado.getCpf());
            paciente.setTelefone(pacienteAtualizado.getTelefone());
            repository.save(paciente);
            return ResponseEntity.ok("Paciente atualizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado para atualização");
        }
    }

    // DELETAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        Optional<Paciente> paciente = repository.findById(id);
        if (paciente.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("Paciente deletado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado para exclusão");
        }
    }
}
