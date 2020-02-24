package demos.hibernate;

import demos.hibernate.model.Student;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class Demo_query {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()) {


            // Hibernate retrieve data by Query
            session.beginTransaction();
            session.setHibernateFlushMode(FlushMode.MANUAL);

            List<Student> students = session
                    .createQuery("FROM Student", Student.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
//                    .getResultStream()
                    .list();

            session.getTransaction().commit();

            System.out.println("Hibernate retrieve data by Query");
            for (Student s : students) { System.out.println(s); }


            // Hibernate retrieve data by Criteria (Typesave)
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery();
            Root<Student> root = criteria.from(Student.class);

            criteria.select(root).where(builder.like(root.get("name"),"Ivan%"));
            List<Student> studentList = session.createQuery(criteria).getResultList();

            System.out.println("Hibernate retrieve data by Criteria");
            for (Student s : students) { System.out.println(s.getName()); }

            session.getTransaction().commit();
        }
    }
}
