package Exercises;

import common.Connector;
import common.Output;
import common.Qry;
import common.Reader;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Problem6 implements Problematic {
    private Connector connector;
    private Qry qry;
    private PreparedStatement preparedStatement;
    private Reader reader;
    private static String description = "Remove Villain";

    public Problem6(Connector connector, Qry qry, Reader reader) {
        this.connector = connector;
        this.qry = qry;
        this.reader = reader;
    }

    private void currentProblem() throws SQLException {

        // Villain ID
        String input = reader.readLine("Enter Villain id:");
        int villainId;

        try {
            villainId = Integer.parseInt(input);

        } catch (NumberFormatException e) {
            System.out.print(Output.badInput);
            return;
        }

        // Villain name
        String villainName = qry.nameById(villainId, "villains");

        if(villainName == null){
            System.out.println("No such villain was found");
            return;
        }

        // delete released
        connector.setQuery("delete from minions_villains mv where mv.villain_id = ?;");
        preparedStatement = connector.createPreparedStatement();
        preparedStatement.setInt(1, villainId);

        int released = preparedStatement.executeUpdate();

        // delete villain
        connector.setQuery("delete from villains v where v.id = ?;");
        preparedStatement = connector.createPreparedStatement();
        preparedStatement.setInt(1, villainId);
        preparedStatement.executeUpdate();

        // output
        System.out.printf("%s was deleted%n", villainName);
        System.out.printf("%d minions released%n", released);
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
