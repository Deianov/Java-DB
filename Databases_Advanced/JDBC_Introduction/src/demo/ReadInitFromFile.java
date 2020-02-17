package demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ReadInitFromFile {
    public static void main(String[] args) throws IOException, SQLException {

        String url = "jdbc:mysql://localhost:3306/soft_uni?useSSL=false";

        Properties properties = new Properties();

        // Init from db.properties file
        String appConfigPath = ReadInitFromFile
                .class
                .getClassLoader()
                .getResource("db.properties")
                .getPath();

        properties.load(new FileInputStream(appConfigPath));

        // Connect to DB
        Connection connection = DriverManager.getConnection(url, properties);
        System.out.println("Connected successfully.");

        String sql = "SELECT * FROM employees WHERE salary > ?";
        PreparedStatement stmt = connection.prepareStatement(sql);

        String salaryStr = properties.getProperty("salary", "20000");
        double salary = Double.parseDouble(salaryStr);

        stmt.setDouble(1, salary);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {

            System.out.printf("| %-15.15s | %-15.15s | %10.2f |%n",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getDouble("salary")
            );
        }
        connection.close();
    }
}
