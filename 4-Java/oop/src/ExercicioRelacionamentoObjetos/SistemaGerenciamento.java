package ExercicioRelacionamentoObjetos;

public class SistemaGerenciamento {
    public static void main(String[] args) {
        Veiculo carro = new Carro("Toyota", "Etios", 2021);
        Veiculo moto = new Moto("Yamaha", "Fazer", 2012);

        Proprietario proprietario = new Proprietario("Juan", "Rua do Juan");

        proprietario.adicionaVeiculo(carro);
        proprietario.adicionaVeiculo(moto);

        proprietario.exibeVeiculos();

        Servico servico1 = new Servico("Troca de óleo", 100.00);
        Servico servico2 = new Servico("Revisão Geral de 10 mil", 500.00);

        Oficina oficina = new Oficina();
        oficina.adicionarServico(servico1);
        oficina.adicionarServico(servico2);

        oficina.listarServicos();

    }
}
