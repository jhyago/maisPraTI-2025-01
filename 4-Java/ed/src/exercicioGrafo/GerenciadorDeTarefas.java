package exercicioGrafo;

//Crie um sistema para gerenciar um conjunto de tarefas de um projeto, utilizando grafos, onde cada tarefa pode depender de outras para ser executada. Utilize Collections, Streams e tratamento de exceções com try-catch para validar e processar os dados.

//Você deve criar uma classe Tarefa que representa uma tarefa do projeto, com os atributos nome, duração (em dias) e uma lista de outras tarefas das quais depende. Crie também uma classe GrafoDeTarefas para gerenciar as tarefas, permitindo adicionar novas tarefas, remover tarefas existentes e calcular a duração total de uma tarefa, levando em conta as suas dependências.

//Ao trabalhar com as dependências, o sistema deve ser capaz de identificar e evitar dependências circulares, ou seja, quando uma tarefa depende de outra que, por sua vez, depende da primeira, criando um ciclo infinito. Caso isso aconteça, o sistema deve lançar uma exceção personalizada, DependenciaCircularException. Caso uma tarefa que não existe seja referenciada, uma outra exceção personalizada, TarefaNaoEncontradaException, deve ser lançada.

//Além disso, o sistema deve permitir o uso de Streams para filtrar e exibir tarefas que possuem dependências, calcular a duração total de uma tarefa (somando a duração da tarefa e de todas as suas dependências) e listar todas as tarefas cuja duração seja superior a um valor específico.

//Implemente o tratamento de exceções para garantir que, ao tentar calcular a duração total de uma tarefa ou adicionar uma tarefa com dependências inválidas, erros sejam capturados e mensagens apropriadas sejam exibidas.

public class GerenciadorDeTarefas {

    public static void main(String[] args) throws TarefaNaoEncotradaException, DepenciaCircularException {
        Tarefa tarefa1 = new Tarefa("Resolver os problemas nos circuitos do tentáculo do Dr. Octavius", 5);
        Tarefa tarefa2 = new Tarefa("Dar água pro peixe", 2);
        Tarefa tarefa3 = new Tarefa("Encher a praia de terra", 3);
        Tarefa tarefa4 = new Tarefa("Resolve os exercícios de Desenvolvimento", 10);

        tarefa1.addDependecia(tarefa2);
        tarefa2.addDependecia(tarefa3);
        tarefa3.addDependecia(tarefa4);

        GrafoDeTarefas grafo = new GrafoDeTarefas();

        grafo.adicionarTarefa(tarefa1);
        grafo.adicionarTarefa(tarefa2);
        grafo.adicionarTarefa(tarefa3);
        grafo.adicionarTarefa(tarefa4);

        int duracaoTotal1 = grafo.calcularDuracaoTotal("Resolver os problemas nos circuitos do tentáculo do Dr. Octavius");
        System.out.println("Tempo total da tarefa 1: " + duracaoTotal1 + " tempos");

        System.out.println("Tarefas com mais de 3 tempos: ");
        grafo.listarTarefasComDuracaoMaiorQue(3).forEach(tarefa -> System.out.println(tarefa.getNome()));

        System.out.println("Tarefas dependentes: ");
        grafo.listarTarefasComDependencias().forEach(tarefa -> System.out.println(tarefa.getNome()));
    }
}
