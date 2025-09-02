public class Pessoa {
    private String nome;
    private int idade;
    protected String sexo;

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public void apresentar() {
        System.out.println("Olá, meu nome é " + nome + " e eu tenho " + idade + " anos de idade");
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

class Main {
    public static void main(String[] args) {
        Pessoa pessoa1 = new Pessoa("Pedro", 20);
        pessoa1.apresentar();

        System.out.println(pessoa1.getNome());
        pessoa1.setNome("Gilmar");
        pessoa1.apresentar();

        Pessoa pessoa2 = new Pessoa("Maria", 30);
        pessoa2.apresentar();
    }
}
