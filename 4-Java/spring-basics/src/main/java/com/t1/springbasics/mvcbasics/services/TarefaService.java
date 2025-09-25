package com.t1.springbasics.mvcbasics.services;

import com.t1.springbasics.mvcbasics.model.Tarefa;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TarefaService {
    private final Map<Long, Tarefa> banco = new LinkedHashMap<>();
    private final AtomicLong idSequence = new AtomicLong();

    public List<Tarefa> listarTarefas() {
        return new ArrayList<>(banco.values());
    }

    public Tarefa criar(String titulo) {
        Long id = idSequence.getAndIncrement();
        Tarefa t = new Tarefa(id, titulo, false);
        banco.put(id, t);
        return t;
    }

    public Optional<Tarefa> buscar(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    public Optional<Tarefa> marcarConcluida(Long id) {
        Tarefa t = banco.get(id);
        if(t != null) {
            t.setConcluida(true);
            return Optional.of(t);
        }
        return Optional.empty();
    }

    public boolean remover(Long id) {
        return banco.remove(id) != null;
    }
}
