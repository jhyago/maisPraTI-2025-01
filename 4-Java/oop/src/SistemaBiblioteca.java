public class SistemaBiblioteca {
    public static void main(String[] args) {
        Biblioteca Vanguarda = new Biblioteca();

        Livro HarryPotter = new Livro("Harry Potter: E o Prisioneiro de Azkaban", "J. k. Rolling", 2003);
        Livro SenhorDosAneis = new Livro("A Sociedade do Anel", "J. R. R. Tolkien", 1954);
        Livro Narnia = new Livro("Nárnia", "C. S. Lewis", 1954);
        Livro Conde = new Livro("Conde de Monte Cristo", "Alexandre Dumas", 1846);
        Livro Despossuidos = new Livro("Os Despossuidos", "Úrsula Le Guin", 1974);
        Livro Memorias = new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1903);
        Livro Bichos = new Livro("Revolução dos Bichos", "George Orwell", 1980);
        Livro Dracule = new Livro(" Drácula", "Bran Stoker", 1897);
        Livro CodigoDaVinci = new Livro("Código da Vinci", "Dan Brown", 2003);
        Livro Suor = new Livro("Suor", "Jorge Amado", 1950);

        Vanguarda.adicionarLivro(HarryPotter);
        Vanguarda.adicionarLivro(SenhorDosAneis);
        Vanguarda.adicionarLivro(Narnia);
        Vanguarda.adicionarLivro(Conde);
        Vanguarda.adicionarLivro(Despossuidos);
        Vanguarda.adicionarLivro(Memorias);
        Vanguarda.adicionarLivro(Bichos);
        Vanguarda.adicionarLivro(Dracule);
        Vanguarda.adicionarLivro(CodigoDaVinci);
        Vanguarda.adicionarLivro(Suor);

//        Vanguarda.listarLivros();

        Vanguarda.alterarDisponibilidade("Código da Vinci", false);
        Vanguarda.listarLivros();
    }
}
