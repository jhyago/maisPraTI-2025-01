package ExercicioRelacionamentoObjetos;

import java.util.ArrayList;
import java.util.List;

public class Oficina {
    private List<Servico> servicos;

    public Oficina() {
        servicos = new ArrayList<>();
    }

    public void adicionarServico(Servico servico) {
        servicos.add(servico);
    }

    public void listarServicos() {
        for (Servico servico : servicos) {
            servico.exibirServico();
            System.out.println();
        }
    }
}
