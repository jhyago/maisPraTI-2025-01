package exercicioGrafo;

import java.util.*;

public class GrafoDeTarefas {
    private Map<String, Tarefa> tarefas;

    public GrafoDeTarefas() {
        tarefas = new HashMap<>();
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.put(tarefa.getNome(), tarefa);
    }

    public void removerTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa.getNome());
    }

    public int calcularDuracaoTotal(String nome) throws TarefaNaoEncotradaException, DepenciaCircularException {
        Tarefa tarefa = tarefas.get(nome);
        if(tarefa == null) {
            throw new TarefaNaoEncotradaException("Tarefa não encontrada: " + nome);
        }

        Set<Tarefa> visitadas = new HashSet<>();
        return calcularDuracaoTotalRecursiva(tarefa, visitadas);
    }

    private int calcularDuracaoTotalRecursiva(Tarefa tarefa, Set<Tarefa> visitadas) throws DepenciaCircularException {
        if(visitadas.contains(tarefa)) {
            throw new DepenciaCircularException("Dependência circular: " + tarefa.getNome());
        }

        visitadas.add(tarefa);
        int duracaoTotal = tarefa.getDuracao();

        for(Tarefa dependencia : tarefa.getDependecias()) {
            duracaoTotal += calcularDuracaoTotalRecursiva(dependencia, visitadas);
        }

        visitadas.remove(tarefa);
        return duracaoTotal;
    }

    public List<Tarefa> listarTarefasComDuracaoMaiorQue(int duracaoMinima) {
        return tarefas
                .values()
                .stream()
                .filter(t -> t.getDuracao() < duracaoMinima)
                .toList();
    }

    public List<Tarefa> listarTarefasComDependencias() {
        return tarefas
                .values()
                .stream()
                .filter(t -> t.getDependecias().isEmpty())
                .toList();
    }
}
