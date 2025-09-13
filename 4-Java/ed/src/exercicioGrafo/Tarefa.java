package exercicioGrafo;

import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.List;

public class Tarefa {
    private String nome;
    private int duracao;
    private List<Tarefa> dependecias;

    public Tarefa(String nome, int duracao) {
        this.nome = nome;
        this.duracao = duracao;
        this.dependecias = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public List<Tarefa> getDependecias() {
        return this.dependecias;
    }

    public void addDependecia(Tarefa tarefa) {
        dependecias.add(tarefa);
    }
}
