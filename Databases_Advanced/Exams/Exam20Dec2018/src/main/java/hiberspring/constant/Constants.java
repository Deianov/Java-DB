package hiberspring.constant;

public final class Constants {

    public final static String PATH_TO_FILES = System.getProperty("user.dir") +
            "/src/main/resources/files/";

    public static final String NOT_FOUND = "Not found in database";
    public static final String EXISTS = "Already exists in database";

    public final static String INCORRECT_DATA_MESSAGE = "Error: Invalid Data!";
    public final static String SUCCESSFUL_IMPORT_MESSAGE = "Successfully imported %s %s.";

    public final static String FILE_BRANCHES = PATH_TO_FILES + "branches.json";
    public final static String FILE_EMPLOYEES = PATH_TO_FILES + "employees.xml";
    public final static String FILE_EMPLOYEES_CARDS = PATH_TO_FILES + "employee-cards.json";
    public final static String FILE_PRODUCTS = PATH_TO_FILES + "products.xml";
    public final static String FILE_TOWNS = PATH_TO_FILES + "towns.json";
}
