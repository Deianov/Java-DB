package demos.hibernate;

import demos.hibernate.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Demo_rename {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            Student student;
            try {
                session.beginTransaction();
                student = session.get(Student.class, 1);
                student.setName(student.getName() + "_rename");
                session.save(student);
                session.getTransaction().commit();

            } catch (Exception e) {
                if (session.getTransaction() != null)
                    session.getTransaction().rollback();
                throw e;
            }
        }
    }
}
