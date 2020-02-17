package Exercises;

import common.Connector;
import common.Output;
import common.Qry;
import common.Reader;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Problem4 implements Problematic {
    private Connector connector;
    private Qry qry;
    private PreparedStatement preparedStatement;
    private Reader reader;
    private static String description = "Add Minion";


    public Problem4(Connector connector, Qry qry, Reader reader) {
        this.connector = connector;
        this.qry = qry;
        this.reader = reader;
    }

    private void currentProblem() throws SQLException {
        String input = reader.readLine();

        String minionName, minionTown;
        int minionAge;
        String EVILNESS_FACTOR = "evil";
        String villainName;
        String[] data;

        // get Minion data
        if (input.isBlank() || input.isEmpty()) {
            System.out.print(Output.badInput);
            return;
        } else {
            data = input.trim().split("\\s+");
        }

        if (data.length == 4 && data[0].equals("Minion:")) {
            minionName = data[1];
            minionAge = Integer.parseInt(data[2]);
            minionTown = data[3];
        } else {
            System.out.print(Output.badInput);
            return;
        }

        // get Villain data
        input = reader.readLine();
        if (input.isBlank() || input.isEmpty()) {
            System.out.print(Output.badInput);
            return;
        } else {
            data = input.trim().split("\\s+");
        }

        if (data.length == 2 && data[0].equals("Villain:")) {
            villainName = data[1];
        } else {
            System.out.print(Output.badInput);
            return;
        }

        // add town if not exist
        if (qry.getIdByValueAndColumn(minionTown, "name", "towns") == -1) {

            String query = "insert into `towns`(`name`) values (?)";

            preparedStatement = connector.createConnection().prepareStatement(query);
            preparedStatement.setString(1, minionTown);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.printf("Town %s was added to the database.%n", minionTown);
            } else {
                System.out.print(Output.unableToUpdateDatabase);
            }
        }

        // add Villain if not exist
        if (qry.getIdByValueAndColumn(villainName, "name", "villains") == -1) {

            String query = "insert into `villains`(`name`, evilness_factor) values (?, ?)";

            preparedStatement = connector.createConnection().prepareStatement(query);
            preparedStatement.setString(1, villainName);
            preparedStatement.setString(2, EVILNESS_FACTOR);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.printf("Villain %s was added to the database.%n", villainName);
            } else {
                System.out.print(Output.unableToUpdateDatabase);
            }
        }

        // add Minion if not exist
        if (qry.getIdByValueAndColumn(minionName, "name", "minions") == -1) {

            String query = "insert into `minions`(`name`, age, town_id) values (?, ?, ?)";

            // get town id
            int townId = qry.getIdByValueAndColumn(minionTown, "name", "towns");
            if (townId == -1){
                System.out.println("Bad town id.");
                return;
            }

            // insert into database
            preparedStatement = connector.createConnection().prepareStatement(query);
            preparedStatement.setString(1, minionName);
            preparedStatement.setInt(2, minionAge);
            preparedStatement.setInt(3, townId);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.printf("Successfully added %s to be minion of %s.%n",
                        minionName,
                        villainName
                );

            } else {
                System.out.print(Output.unableToUpdateDatabase);
            }
        }
    }

    @Override
    public void run() {
        try {
            this.currentProblem();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return description;
    }
}
