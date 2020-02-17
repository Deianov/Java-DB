package Exercises;

import common.*;

public class Main {
    public static void main(String[] args)  {

        // 1.Initial Setup
        Connector connector = new Connector("minions_db", "root", "1234");
        Qry qry = new Qry(connector);
        Reader reader = new Reader();

        // Problems
        Problematic[] problems = {
                new Problem2(connector, qry, reader),
                new Problem3(connector, qry, reader),
                new Problem4(connector, qry, reader),
                new Problem5(connector, qry, reader),
                new Problem6(connector, qry, reader),
                new Problem7(connector, qry, reader),
                new Problem8(connector, qry, reader),
                new Problem9(connector, qry, reader)
        };

        // Run problem
        int problemNumber = -1;

        while (problemNumber < 2 || problemNumber > 9){
            try {
                String input = reader.readLine("Enter problem number [2-9]: ");
                problemNumber = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("Bad number format.\n");
            }
        }

        Problematic problem = problems[problemNumber - 2];

        System.out.printf("%s : %s%nInput:%n",
                problem.getClass().getSimpleName(),
                problem.getDescription()
        );
        problem.run();
    }
}


