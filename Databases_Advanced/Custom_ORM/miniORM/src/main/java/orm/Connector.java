package orm;

import java.sql.*;
import java.util.Properties;

public class Connector {
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    private static Connection connection;
    private static String schema;

    public static void createConnection(String username, String password, String schema) throws SQLException {
        Connector.schema = schema;
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        try {
            connection = DriverManager.getConnection(SERVER_URL + schema, properties);

        } catch (SQLException e1) {
            connection = null;
            Connector.createSchema(properties);
        }

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(SERVER_URL + schema, properties);

            } catch (SQLException e2) {
                throw new SQLException("Connector: Unable to create connection.");
            }
        }
    }

    private static boolean isExistSchema() throws SQLException {
        ResultSet resultSet = connection.getMetaData().getCatalogs();
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(schema)){
                return true;
            }
        }
        resultSet.close();
        return false;
    }

    private static void createSchema(Properties properties) throws SQLException {
        try {
            Connection server = DriverManager.getConnection(SERVER_URL, properties);
            Statement statement = server.createStatement();
            statement.executeUpdate("CREATE SCHEMA " + schema);
            System.out.println("Create schema: " + schema);

        } catch (SQLException e) {
            throw new SQLException("Connector: Unable to create schema.");
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static String getSchema() {
        return schema;
    }
}
