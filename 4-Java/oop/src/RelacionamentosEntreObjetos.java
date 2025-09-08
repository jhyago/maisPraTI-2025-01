import java.util.ArrayList;
import java.util.List;

public class RelacionamentosEntreObjetos {

    static class Motorista {
        private String nome;

        public Motorista(String nome) {
            this.nome = nome;
        }

        public void dirigir(Carro carro) {
            System.out.println(this.nome + " está dirigindo o carro " + carro.getModelo());
        }
    }

    static class Carro {
        private String modelo;

        public Carro(String modelo) {
            this.modelo = modelo;
        }

        public String getModelo() {
            return this.modelo;
        }
    }

    static class Curso {
        private String nome;
        private List<Aluno> alunos;

        public Curso(String nome) {
            this.nome = nome;
            this.alunos = new ArrayList<>();
        }

        public void adicionarAluno(Aluno aluno) {
            this.alunos.add(aluno);
            System.out.println(aluno.getNome() + " Foi adicionado ao curso " + this.nome);
        }
    }

    static class Aluno {
        private String nome;

        public Aluno(String nome) {
            this.nome = nome;
        }

        public String getNome() {
            return this.nome;
        }
    }

    static class Computador {
        private CPU cpu;

        public Computador(String modeloCPU) {
            this.cpu = new CPU(modeloCPU);
        }

        public void ligar() {
            System.out.println("Ligando o computador com CPU " + cpu.getModelo());
        }
    }

    private static class CPU {
        private String modelo;

        public CPU(String modelo) {
            this.modelo = modelo;
        }

        public String getModelo() {
            return this.modelo;
        }
    }

    static class Impressora {
        public void imprimir (Documento documento) {
            System.out.println("Imprimindo Documento: " + documento.getConteudo());
        }
    }

    static class Documento {
        private String conteudo;
        public Documento(String conteudo) {
            this.conteudo = conteudo;
        }

        public String getConteudo() {
            return this.conteudo;
        }
    }

    public static void main(String[] args) {
        System.out.println("==== Associação ====");
        Motorista m1 = new Motorista("Braia");
        Carro c1 = new Carro("Skyline");
        m1.dirigir(c1);

        System.out.println("==== Agregação ====");
        Curso curso = new Curso("Engenharia de Software");
        Aluno alu1 = new Aluno("Fábio");
        Aluno alu2 = new Aluno("Marcos");

        curso.adicionarAluno(alu1);
        curso.adicionarAluno(alu2);

        System.out.println("==== Composição ====");

        Computador  computador = new Computador("Intel Core i9");
        computador.ligar();

        System.out.println("==== Dependência ====");
        Documento documento = new Documento("Contrato");
        Impressora impressora = new Impressora();
        impressora.imprimir(documento);
    }

// Implemente um sistema de gerenciamento de veículos e seus proprietários. O sistema deve utilizar os conceitos de associação, agregação, composição, herança, polimorfismo e dependência.
//
// O sistema deve ter uma classe abstrata chamada Veiculo, com atributos básicos como marca, modelo e ano, e um método para exibir essas informações. Além disso, o método tipoDeVeiculo() deverá ser implementado nas subclasses Carro e Moto, que devem herdar de Veiculo. Cada tipo de veículo deve exibir seu tipo específico.
//
// Crie uma classe Proprietario, que possui informações como nome e endereço, e que pode ter múltiplos veículos associados a ele. A relação entre proprietário e veículos deve ser uma associação.
//
// Implemente uma classe Servico, que tem informações sobre a descrição e o preço dos serviços realizados, e uma classe Oficina, que é responsável por adicionar e listar serviços realizados.
//
// Entre as classes, estabeleça as relações necessárias, como agregação entre oficina e serviços e dependência entre proprietário e veículos. Utilize herança e polimorfismo para organizar e expandir o sistema de forma eficiente.
}
