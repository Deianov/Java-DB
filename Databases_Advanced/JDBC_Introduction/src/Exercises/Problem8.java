package Exercises;

import common.Connector;
import common.Output;
import common.Qry;
import common.Reader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Problem8  implements Problematic{
    private Connector connector;
    private Qry qry;
    private PreparedStatement preparedStatement;
    private Reader reader;
    private static String description = "Increase Minions Age";

    public Problem8(Connector connector, Qry qry, Reader reader) {
        this.connector = connector;
        this.qry = qry;
        this.reader = reader;
    }

    private void currentProblem() throws SQLException {

        String input = reader.readLine();

        if (input.isEmpty() || input.isBlank()){
            System.out.print(Output.badInput);
            return;
        }

        int[] IDs = Arrays
                .stream(input.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int length = IDs.length;

        // update minions
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                "update minions m " +
                "set m.name = lower(m.name), m.age = m.age + 1 " +
                "where m.id in("
        );

        for (int i = 0; i < length; i++) {
            stringBuilder.append((i < length - 1) ? "?," : "?)");
        }

        connector.setQuery(stringBuilder.toString());
        preparedStatement = connector.createPreparedStatement();

        int index = 1;
        for (int id : IDs) {
            preparedStatement.setInt(index++, id);
        }
        preparedStatement.executeUpdate();

        // print all minions
        connector.setQuery("select `name`,age from minions");
        preparedStatement = connector.createPreparedStatement();
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.print("Output:\n");
        while (resultSet.next()){
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
