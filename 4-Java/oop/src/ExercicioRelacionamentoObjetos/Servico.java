package ExercicioRelacionamentoObjetos;

public class Servico {
    private String descricao;
    private double preco;

    public Servico(String descricao, double preco) {
        this.descricao = descricao;
        this.preco = preco;
    }

    public void exibirServico() {
        System.out.println("Descrição: " + descricao);
        System.out.println("Preço: " + preco);
    }
}
