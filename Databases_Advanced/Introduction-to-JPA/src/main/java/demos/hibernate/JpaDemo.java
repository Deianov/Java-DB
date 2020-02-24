package demos.hibernate;

import demos.hibernate.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaDemo {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("school");
        EntityManager entityManager = factory.createEntityManager();

        User user = new User("Jon Snow");

        // add user
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        // read
        User u = entityManager.find(User.class, 1);
        System.out.println(u);

        // query
        entityManager.getTransaction().begin();
        entityManager
                .createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();

        // query by Criteria
        entityManager.getTransaction().begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = builder.createQuery();
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root).where(builder.like(root.get("name"), "Jon%"));
        entityManager
                .createQuery(criteriaQuery)
                .getResultList()
                .forEach(System.out::println);

        entityManager.getTransaction().commit();
    }
}
