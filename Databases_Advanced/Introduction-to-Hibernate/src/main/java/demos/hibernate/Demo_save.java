package demos.hibernate;

import demos.hibernate.model.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Demo_save {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            // create Student
            Student student = new Student("Ivan Petrov");
            try {
                session.beginTransaction();
                session.save(student);
                session.getTransaction().commit();

            } catch (Exception e) {
                if (session.getTransaction() != null)
                    session.getTransaction().rollback();
                throw e;
            }

            // read from database
            session.beginTransaction();
            session.setHibernateFlushMode(FlushMode.MANUAL);

            // optional LockMode
            student = session.get(Student.class, 1, LockMode.PESSIMISTIC_READ);
            session.getTransaction().commit();
            System.out.println(student);

        }
    }
}
