package demo;

import common.Reader;
import java.sql.*;
import java.util.Properties;

public class ReadWhitException {
    public static void main(String[] args)  {

        String connection_string = "jdbc:mysql://localhost:3306/";
        String schema = "soft_uni";
        String user = "root";
        Reader reader = new Reader();
        String password = reader.readLine("Password: ");

        // Properties
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);


        String sql = "SELECT * FROM employees WHERE salary > ?";

        try (
                Connection connection = DriverManager.getConnection(String.format("%s%s?useSSL=false", connection_string, schema), properties);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
            ){

            preparedStatement.setDouble(1, 20000);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                System.out.printf("| %-15.15s | %-15.15s | %10.2f |%n",
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
