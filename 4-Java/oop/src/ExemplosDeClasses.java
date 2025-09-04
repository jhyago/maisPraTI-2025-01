public class ExemplosDeClasses {
    public static class ClasseEstatica {
        public static void mostrarMensagem() {
            System.out.println("Mensagem do Classe Estática");
        }
    }

    public class ClasseInterna {
        public void mostrarMensagem() {
            System.out.println("Mensagem do Classe Interna");
        }
    }

    public void exemploClasseLocal() {
        class ClasseLocal {
            public void mostrarMensagem() {
                System.out.println("Mensagem do Classe Local");
            }
        }

        ClasseLocal classeLocal = new ClasseLocal();
        classeLocal.mostrarMensagem();
    }

    public abstract class ClasseAbstrata {
        public abstract void mostrarMensagem();
    }

    public class SubClasseAbstrata extends ClasseAbstrata {
        @Override
        public void mostrarMensagem() {
            System.out.println("Mensagem do SubClasse Abstrata");
        }
    }

    public sealed class ClasseSelada permits SubClassePermitida {
        public void mostrarMensagem() {
            System.out.println("Mensagem do Classe Selada");
        }
    }

    public final class SubClassePermitida extends ClasseSelada {
        @Override
        public void mostrarMensagem() {
            System.out.println("Mensagem do SubClasse Permitida");
        }
    }

    public record ClasseRecord(String nome, int idade) {

    }

    public static void main(String[] args) {
        ClasseEstatica.mostrarMensagem();
        ClasseRecord record = new ClasseRecord("Jaques", 20);
        System.out.println(record.nome() + record.idade());
    }
}

//Crie um sistema simples de biblioteca utilizando conceitos básicos de classes em Java.
//O sistema deve ser composto por uma classe que represente um livro e uma classe para a biblioteca.

//A classe Livro deve ter os atributos título, autor, ano de publicação e disponibilidade (se o livro está disponível ou emprestado).
// Além disso, ela deve permitir a alteração da disponibilidade e a exibição das informações do livro.


//A classe Biblioteca deve conter um conjunto de livros e permitir adicionar novos livros, além de listar todos os livros cadastrados.
//O sistema deve possibilitar também a alteração da disponibilidade de um livro.

//Implemente uma classe principal para testar a funcionalidade do sistema, criando instâncias de livros e de uma biblioteca, e alterando as disponibilidades dos livros.
