package demos.hibernate;

import demos.hibernate.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Demo_update {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            Student student = new Student("Ivan Petrov");
            try {
                session.beginTransaction();
                session.save(student);
                session.detach(student);
                student.setName("I. Petrov");
                // difference between save and persist is that persist throws exception when trying to persist detached entity
                // save -> 2 entities
                // persist -> throws exception
                // merge/update -> 1 entity ("I. Petrov")
                session.update(student);
                session.getTransaction().commit();
            } catch (Exception e) {
                if (session.getTransaction() != null)
                    session.getTransaction().rollback();
                throw e;
            }
        }
    }
}