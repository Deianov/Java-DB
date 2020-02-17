package Exercises;

import common.Connector;
import common.Output;
import common.Qry;
import common.Reader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Problem5 implements Problematic {
    private Connector connector;
    private Qry qry;
    private PreparedStatement preparedStatement;
    private Reader reader;
    private static String description = "Change Town Names Casing";

    public Problem5(Connector connector, Qry qry, Reader reader) {
        this.connector = connector;
        this.qry = qry;
        this.reader = reader;
    }

    private void currentProblem() throws SQLException {

        String input = reader.readLine("Enter country: \n");

        if (input.isEmpty() || input.isBlank()){
            System.out.print(Output.badInput);
            return;
        }
        String query = "update towns t set t.name = upper(t.name) where t.country = ?";

        preparedStatement = connector.createConnection().prepareStatement(query);
        preparedStatement.setString(1, input);

        int affectedRows = preparedStatement.executeUpdate();

        if(affectedRows > 0){
            System.out.printf("%d town names were affected.%n", affectedRows);
            String[] townNames = new String[affectedRows];

            // get updated names
            connector.setQuery("select `name` from towns t where t.country = ?");
            preparedStatement = connector.createPreparedStatement();
            preparedStatement.setString(1, input);

            ResultSet resultSet = preparedStatement.executeQuery();
            int index = 0;
            while (resultSet.next()) {
                townNames[index++] = resultSet.getString(1);
            }
            System.out.printf("%s%n", Arrays.toString(townNames));

        } else {
            System.out.print("No town names were affected.\n");
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
