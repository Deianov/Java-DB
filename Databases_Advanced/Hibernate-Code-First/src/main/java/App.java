import constants.PersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    // Change if you want
    private static final PersistenceUnit PERSISTENCE_UNIT =
            PersistenceUnit.code_first_demo;

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(PERSISTENCE_UNIT.toString());

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Engine engine = new Engine(entityManager, PERSISTENCE_UNIT);
        engine.run();
    }
}
