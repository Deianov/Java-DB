import constants.PersistenceUnit;

import javax.persistence.EntityManager;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private final PersistenceUnit persistenceUnit;

    Engine(EntityManager entityManager, PersistenceUnit persistenceUnit) {
        this.entityManager = entityManager;
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    public void run() {

        switch (persistenceUnit){
            case code_first_demo :
                System.out.println("Demo: Hibernate Code First");
                break;
            case code_first_ex :
                System.out.println("Exercise: Hibernate Code First");
                break;
        }
    }
}
