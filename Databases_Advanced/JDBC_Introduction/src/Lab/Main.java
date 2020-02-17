package Lab;


import common.Reader;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        String driver = "com.mysql.cj.jdbc.Driver";
        String connection_string = "jdbc:mysql://localhost:3306/";
        String schema = "diablo";
        String user = "root";
        Reader reader = new Reader();
        String password = reader.readLine("Enter SQL password: ");


        // Properties
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        String queryUser = "SELECT id, user_name, first_name, last_name FROM users WHERE user_name = '%s'";
        String queryGames = "SELECT count(*) count from (SELECT DISTINCT game_id from users_games WHERE user_id = %s) ids";

        try (
                Connection connection = DriverManager.getConnection(String.format("%s%s?useSSL=false", connection_string, schema), properties);
                Statement statement = connection.createStatement()
            ) {

            String userName = reader.readLine("User name : ");
            queryUser = String.format(queryUser, userName);

            statement.executeQuery(queryUser);

            ResultSet resultSet = statement.getResultSet();
            String output;
            int userId;

            if (!resultSet.next()) {
                System.out.println("No such user exists");
                return;

            } else {
                userId = resultSet.getInt("id");

                output = String.format("User: %s%n%s %s has played ",
                        resultSet.getString("user_name"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                    );
            }

            resultSet.close();
            queryGames = String.format(queryGames, userId);

            statement.executeQuery(queryGames);
            resultSet = statement.getResultSet();


            int playedGames = 0;

            if (resultSet.next()) {
                playedGames = resultSet.getInt("count");
            }

            System.out.printf("%s%d games", output, playedGames);

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
