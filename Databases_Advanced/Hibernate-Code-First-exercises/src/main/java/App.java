import constants.PersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    // Change if you want
    private static final PersistenceUnit PERSISTENCE_UNIT =
            PersistenceUnit.code_first_ex;

    /*
    1.) Use import Project
    2.) Change persistence.xml -> user, password
    3.) Test the first task
    4.) entities -> right click -> Show in Explorer -> zip, delete, unzip...
    */

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(PERSISTENCE_UNIT.toString());

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Engine engine = new Engine(entityManager, PERSISTENCE_UNIT);
        engine.run();
    }
}
