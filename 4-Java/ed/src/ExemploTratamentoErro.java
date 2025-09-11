import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExemploTratamentoErro {
    public static void main(String[] args) throws FileNotFoundException {
//        try {
//            int [] numeros = {1, 2, 3};
//            System.out.println(numeros[5]);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println("Erro: índice fora dos limites do array!");
//            e.printStackTrace();
//        } finally {
//            System.out.println("Este bloco é sempre executado, mesmo que ocorra uma exception");
//        }
//
//        try {
//            FileReader fr = new FileReader("arquivo.txt");
//        } catch (IOException e) {
//            System.out.println("Erro: Erro ao abrir arquivo!" + e.getMessage());
//        }
//
//        try {
//            String texto = null;
//            System.out.println(texto.length());
//        } catch (NullPointerException e) {
//            System.out.println("Erro: Tentativa de acessar um método em um objeto null!" + e.getMessage());
//        }
//
//        abrirArquivo();

        try {
            throw new MyException("Erro Personalizado!");
        } catch (MyException e) {
            System.out.println("Erro Personalizado!" + e.getMessage());
        }
    }

    public static void abrirArquivo() throws FileNotFoundException {
        FileReader fr = new FileReader ("arquivo.txt");
    }

//    Crie um sistema para gerenciar um conjunto de tarefas de um projeto, utilizando grafos, onde cada tarefa pode depender de outras para ser executada. Utilize Collections, Streams e tratamento de exceções com try-catch para validar e processar os dados.

//    Você deve criar uma classe Tarefa que representa uma tarefa do projeto, com os atributos nome, duração (em dias) e uma lista de outras tarefas das quais depende. Crie também uma classe GrafoDeTarefas para gerenciar as tarefas, permitindo adicionar novas tarefas, remover tarefas existentes e calcular a duração total de uma tarefa, levando em conta as suas dependências.

//    Ao trabalhar com as dependências, o sistema deve ser capaz de identificar e evitar dependências circulares, ou seja, quando uma tarefa depende de outra que, por sua vez, depende da primeira, criando um ciclo infinito. Caso isso aconteça, o sistema deve lançar uma exceção personalizada, DependenciaCircularException. Caso uma tarefa que não existe seja referenciada, uma outra exceção personalizada, TarefaNaoEncontradaException, deve ser lançada.

//    Além disso, o sistema deve permitir o uso de Streams para filtrar e exibir tarefas que possuem dependências, calcular a duração total de uma tarefa (somando a duração da tarefa e de todas as suas dependências) e listar todas as tarefas cuja duração seja superior a um valor específico.

//    Implemente o tratamento de exceções para garantir que, ao tentar calcular a duração total de uma tarefa ou adicionar uma tarefa com dependências inválidas, erros sejam capturados e mensagens apropriadas sejam exibidas.
//
}
