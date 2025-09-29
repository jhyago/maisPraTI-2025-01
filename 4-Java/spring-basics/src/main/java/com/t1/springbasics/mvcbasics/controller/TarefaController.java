package com.t1.springbasics.mvcbasics.controller;

import com.t1.springbasics.mvcbasics.model.Tarefa;
import com.t1.springbasics.mvcbasics.services.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {
    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tarefa> listar() {
        return service.listarTarefas();
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa novaTarefa) {
        Tarefa criada = service.criar(novaTarefa.getTitulo());
        return ResponseEntity.created(URI.create("/api/tarefas/" + criada.getId())).body(criada);
    }

    @PutMapping
    public ResponseEntity<Tarefa> marcarConcluida(@RequestParam Long id) {
        Tarefa t = service.marcarConcluida(id).orElse(null);
        if(t != null) {
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }
}
