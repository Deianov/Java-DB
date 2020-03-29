package softuni.exam.constant;

public final class Constants {

    // file paths
    public final static String CARS_FILE_PATH = "src/main/resources/files/json/cars.json";
    public final static String PICTURES_FILE_PATH = "src/main/resources/files/json/pictures.json";
    public final static String OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";
    public final static String SELLERS_FILE_PATH = "src/main/resources/files/xml/sellers.xml";

    // database
    public static final String NOT_FOUND = "Not found in database";
    public static final String EXISTS = "Already exists in database";

    // messages
    public final static String INCORRECT_DATA_MESSAGE = "Invalid %s";
    public final static String SUCCESSFUL_IMPORT_MESSAGE = "Successfully imported %s - %s - %s";
    public final static String SUCCESSFUL_IMPORT_PICTURE = "Successfully imported %s - %s";
    public final static String SUCCESSFUL_IMPORT_SELLER = "Successfully imported seller %s - %s";
    public final static String SUCCESSFUL_IMPORT_OFFER = "Successfully imported offer %s - %s";

    public final static String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String LOCAL_DATE_FORMAT = "dd/MM/yyyy";

    // validation
    public static final String EMAIL_VALIDATE_REGEX = "\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,63}\\b";
}
