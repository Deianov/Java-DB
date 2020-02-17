package common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Qry {
    private Connector connector;
    private PreparedStatement preparedStatement;

    public Qry(Connector connector) {
        this.connector = connector;
    }


    public String nameById(int id, String tableName) {
        connector.setQuery("SELECT `name` FROM " + tableName + " WHERE id = ?;");

        preparedStatement = connector.createPreparedStatement();
        ResultSet resultSet;
        String result = null;

        try {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            result = resultSet.next() ? resultSet.getString(1) : null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public int getIdByValueAndColumn(String value, String columnName, String tableName)  {
        connector.setQuery("SELECT * FROM `" + tableName + "` WHERE `" + columnName +"` = ?");

        preparedStatement = connector.createPreparedStatement();
        int id = -1;

        try {
            preparedStatement.setString(1, value);

            ResultSet resultSet = preparedStatement.executeQuery();

            id = resultSet.next() ? resultSet.getInt(1) : -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    public ResultSet resultSetById(int id, String tableName) {
        connector.setQuery("SELECT * FROM " + tableName + " WHERE id = ?;");

        preparedStatement = connector.createPreparedStatement();
        ResultSet resultSet = null;

        try {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultSet;
    }
}
