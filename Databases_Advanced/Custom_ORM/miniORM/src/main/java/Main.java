import entities.Country;
import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {

        // connection
        Connector.createConnection("root", "1234", "orm");

        // Class: User
        EntityManager<User> managerUsers = new EntityManager<>(Connector.getConnection(), User.class);

        managerUsers.persist(new User("test_user1", "1234", 17, new Date()));
        managerUsers.persist(new User("test_user2", "1234", 22, new Date()));

        // Class: Country
        EntityManager<Country> managerCountries = new EntityManager<>(Connector.getConnection(), Country.class);

        managerCountries.persist(new Country("Bulgaria", "BGN"));
        managerCountries.persist(new Country("Germany", "EUR"));

        // test queries
        managerUsers.listEntity(managerUsers.findFirst(User.class, "age > 16"));

        for (User user: managerUsers.find(User.class, "age > 20")) {
            managerUsers.listEntity(user);
        }
    }
}
