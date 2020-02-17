package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    // driver
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // connection string
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    // current schema
    private String schema;
    // SSL
    private static boolean useSSL = false;

    private Properties properties;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String query;

    public Connector(String schema, String user, String password) {
        this.properties = new Properties();
        this.schema = schema;
        this.setProperties(user, password);
        this.createConnection();
    }

    private void setProperties(String user, String password){
        if (user != null) {
            this.properties.setProperty("user", user);
        }
        if (password != null) {
            this.properties.setProperty("password", password);
        }
    }

    public Connection createConnection()  {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.connection = null;
        }

        try {
            this.connection = DriverManager.getConnection(
                    String.format("%s%s?useSSL=%b", CONNECTION_STRING, schema, useSSL), this.properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.connection;
    }

    public PreparedStatement createPreparedStatement() {
        this.preparedStatement = null;
        try {
            this.preparedStatement = this.connection.prepareStatement(this.query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.preparedStatement;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Connection getConnection() {
        return connection;
    }
}