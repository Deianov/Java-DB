package softuni.exam.constant;

public final class GlobalConstants {

    public static final String FILE_PATH_PLAYERS =
            "src/main/resources/files/json/players.json";
    public static final String FILE_PATH_PICTURES =
            "src/main/resources/files/xml/pictures.xml";
    public static final String FILE_PATH_TEAMS =
            "src/main/resources/files/xml/teams.xml";

    public static final String NOT_FOUND = "Not found in database";
    public static final String EXISTS = "Already exists in database";

    public static final String IMPORTED_PICTURE = "Successfully imported picture - %s";
    public static final String INVALID_PICTURE = "Invalid picture";

    public static final String IMPORTED_TEAM = "Successfully imported - %s";
    public static final String INVALID_TEAM = "Invalid team";

    public static final String IMPORTED_PLAYER = "Successfully imported player: %s %s";
    public static final String INVALID_PLAYER = "Invalid player";

    public static final String EXPORT_PLAYERS_FROM_TEAM = "North Hub";
    public static final String PLAYER_BY_NUMBER_FORMAT =
            "\tPlayer name: %s %s - %s%n\tNumber: %d";

    public static final double EXPORT_PLAYERS_WITH_SALARY_BIGGER_THAN = 100000;
    public static final String PLAYER_BY_SALARY_FORMAT =
            "Player name: %s %s%n\tNumber: %d%n\tSalary: %s%n\tTeam: %s";
}