package Exercises;

import common.Connector;
import common.Qry;
import common.Reader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Problem7 implements Problematic {
    private Connector connector;
    private Qry qry;
    private PreparedStatement preparedStatement;
    private Reader reader;
    private static String description = "Print All Minion Names";

    public Problem7(Connector connector, Qry qry, Reader reader) {
        this.connector = connector;
        this.qry = qry;
        this.reader = reader;
    }

    private void currentProblem() throws SQLException {

        connector.setQuery("select `name` from minions");
        preparedStatement = connector.createPreparedStatement();
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<String> originalOrder = new ArrayList<>();

        while (resultSet.next()){
            originalOrder.add(resultSet.getString(1));
        }

        int size = originalOrder.size();

        if (size > 0) {
            String[] newOrder = new String[size];

            int c = 0;
            for (int i = 0; i < size / 2 ; i++) {

                newOrder[c++] = originalOrder.get(i);
                newOrder[c++] = originalOrder.get(size - 1 - i);
            }
            if (size % 2 != 0){
                newOrder[c] = originalOrder.get(size / 2);
            }

            Arrays.stream(newOrder).forEach(System.out::println);
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
