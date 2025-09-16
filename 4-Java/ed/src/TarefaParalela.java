public class TarefaParalela extends Thread {
    private String nome;

    public TarefaParalela(String nome) {
        this.nome = nome;
    }

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println(this.nome + " está executando no paralelo " + (i + 1));
        }
    }

    public static void main(String[] args) {
        TarefaParalela tarefa1 = new TarefaParalela("Processo 1");
        TarefaParalela tarefa2 = new TarefaParalela("Processo 2");
        tarefa1.start();
        tarefa2.start();
    }
}
