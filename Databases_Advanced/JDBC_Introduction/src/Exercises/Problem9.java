package Exercises;

import common.Connector;
import common.Output;
import common.Qry;
import common.Reader;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Problem9 implements Problematic {
    private Connector connector;
    private Qry qry;
    private PreparedStatement preparedStatement;
    private Reader reader;
    private static String description = "Increase Age Stored Procedure";

    public Problem9(Connector connector, Qry qry, Reader reader) {
        this.connector = connector;
        this.qry = qry;
        this.reader = reader;
    }

    private void currentProblem() throws SQLException {

        int minionId;

        try {
            String input = reader.readLine("Enter minion id: ");
            minionId = Integer.parseInt(input);

        } catch (NumberFormatException e) {
            System.out.print(Output.badInput);
            return;
        }

        String query = "{CALL usp_get_older(?)}";
        CallableStatement statement = connector.getConnection().prepareCall(query);
        statement.setInt(1, minionId);
        statement.executeQuery();

        // get minion by id
        ResultSet resultSet = qry.resultSetById(minionId, "minions");

        if(resultSet.next()){
            System.out.printf("%-10.10s | %d%n",
                    resultSet.getString("name"),
                    resultSet.getInt("age")
            );
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
