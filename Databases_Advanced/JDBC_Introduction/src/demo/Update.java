package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Update {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        String password;

        System.out.print("Enter password : ");
        password = scanner.nextLine().trim();

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", password);

        // Driver
        String driver = "com.mysql.cj.jdbc.Driver";

        // Connection string
        String url = "jdbc:mysql://localhost:3306/soft_uni?useSSL=false";

        // Load jdbc driver (optional)
        Class.forName(driver);

        // Connect to database
        Connection connection = DriverManager.getConnection(url, properties);

        // Update query
        String query = "update employees set salary = ? where first_name = ? and last_name = ?";

        // Create the java mysql update PreparedStatement
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setDouble(1, 12000);
        stmt.setString(2, "Guy");
        stmt.setString(3, "Gilbert");

        // execute the java PreparedStatement
        stmt.executeUpdate();

        connection.close();
    }
}
