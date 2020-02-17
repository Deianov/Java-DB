package Exercises;

import common.Connector;
import common.Qry;
import common.Reader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Problem2 implements Problematic  {
    private Connector connector;
    private Qry qry;
    private PreparedStatement preparedStatement;
    private Reader reader;
    private static String description = "Qry Villains’ Names";

    public Problem2(Connector connector, Qry qry, Reader reader) {
        this.connector = connector;
        this.qry = qry;
        this.reader = reader;
    }

    private void currentProblem() throws SQLException {


        connector.setQuery(
            "select v.name, count(mv.minion_id) 'count' \n" +
            "from villains v\n" +
            "join minions_villains mv on v.id = mv.villain_id\n" +
            "group by v.name\n" +
            "having `count` > 15\n" +
            "order by `count` desc"
        );

        preparedStatement = connector.createPreparedStatement();

        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.print("Output:\n");
        while (resultSet.next()) {
            System.out.printf("%s %d%n",
                  resultSet.getString("name"),
                  resultSet.getInt("count")
            );
        }

    }

    public String getDescription(){
        return description;
    }

    @Override
    public void run() {
        try { this.currentProblem(); } catch (SQLException e) {e.printStackTrace();}
    }
}
