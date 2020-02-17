package demo;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Read {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        String user, password;

        System.out.print("Enter username default (root): ");
        user = scanner.nextLine();
        user = user.equals("") ? "root" : user;

        System.out.print("Enter password default (empty): ");
        password = scanner.nextLine().trim();

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        // Driver
        // MySQL Connector/J 8.0
        String myDriver = "com.mysql.cj.jdbc.Driver";

        // Connection string
        // jdbc:<driver protocol>:<connection details>
        // use username -> root:root@localhost:3306
        String myUrl = "jdbc:mysql://localhost:3306/soft_uni?useSSL=false";

        // Load jdbc driver (optional)
        try {
            Class.forName(myDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Driver loaded successfully.");

        // Connect to DB
        Connection connection = DriverManager.getConnection(myUrl, properties);
        System.out.println("Connected successfully.");

        String sqlQuery = "SELECT * FROM employees WHERE salary > ?";

        // Not updatable
        // Return generated keys : sqlQuery, Statement.RETURN_GENERATED_KEYS
        PreparedStatement stmt = connection.prepareStatement(sqlQuery);

        System.out.print("Enter minimal salary (default 20000): ");
        String salaryStr = scanner.nextLine().trim();
        // NumberFormatException
        double salary = salaryStr.equals("") ? 20000 : Double.parseDouble(salaryStr);

        stmt.setDouble(1, salary);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            // trim to 15, left alg.
            System.out.printf("| %-15.15s | %-15.15s | %10.2f |%n",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getDouble("salary")
                    );
        }
        connection.close();
    }
}
