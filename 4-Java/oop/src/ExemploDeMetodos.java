public class ExemploDeMetodos {

    public static void metodoEstatico() {
        System.out.println("Metodo Estatico");
    }

    public void metodoInstancia() {
        System.out.println("MetodoInstancia");
    }

    public abstract static class ClasseMetodoAbstrato {
        public abstract void metodoAbstrato();
    }

    public static class SubClasseAbstrata extends ClasseMetodoAbstrato {
        @Override
        public void metodoAbstrato() {
            System.out.println("Método Abstrato");
        }
    }

    public final void metodoFinal() {
        System.out.println("MetodoFinal");
    }

    private void metodoPrivado() {
        System.out.println("Privado!");
    }

    public static void main(String[] args) {
        ExemploDeMetodos.metodoEstatico();
        ExemploDeMetodos instancia = new ExemploDeMetodos();
        instancia.metodoInstancia();

        ExemploDeMetodos.SubClasseAbstrata teste = new ExemploDeMetodos.SubClasseAbstrata();
        teste.metodoAbstrato();
    }
}
