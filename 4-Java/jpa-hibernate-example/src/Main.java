import model.Cardapio;
import model.Comanda;
import model.Item_comanda;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().addAnnotatedClass(Cardapio.class).addAnnotatedClass(Comanda.class).addAnnotatedClass(Item_comanda.class).buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {
            Cardapio cafe1 = new Cardapio();
            cafe1.setNome("Pizza");
            cafe1.setDescricao("Pizza de calabresa");
            cafe1.setPreco_unitario(10.0);

            session.save(cafe1);

            session.beginTransaction();

            session.save(cafe1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}