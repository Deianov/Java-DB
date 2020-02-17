package Exercises;

import common.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Problem3 implements Problematic {
    private Connector connector;
    private Qry qry;
    private PreparedStatement preparedStatement;
    private Reader reader;
    private static String description = "Get Minion Names";

    public Problem3(Connector connector, Qry qry, Reader reader) {
        this.connector = connector;
        this.qry = qry;
        this.reader = reader;
    }

    private void currentProblem() throws SQLException {

        int villain_id = Integer.parseInt(reader.readLine("Enter villain id: "));

        ResultSet resultSet = qry.resultSetById(villain_id, "villains");

        if (resultSet.next()) {
            System.out.printf("Villain: %s%n", resultSet.getString("name"));

            connector.setQuery(
                    "select m.name, m.age from minions m\n" +
                    "join minions_villains mv on m.id = mv.minion_id\n" +
                    "where mv.villain_id = ?"
            );

            preparedStatement = connector.createPreparedStatement();
            preparedStatement.setInt(1, villain_id);

            resultSet = preparedStatement.executeQuery();

            int count = 0;

            while (resultSet.next()){
                System.out.printf("%d. %s %d%n",
                        ++count,
                        resultSet.getString("name"),
                        resultSet.getInt("age")
                );
            }

        } else {
            System.out.printf("No villain with ID %d exists in the database.%n", villain_id);
        }
    }

    @Override
    public void run() {
        try { this.currentProblem(); } catch (SQLException e) {e.printStackTrace();}
    }


    @Override
    public String getDescription() {
        return description;
    }
}
