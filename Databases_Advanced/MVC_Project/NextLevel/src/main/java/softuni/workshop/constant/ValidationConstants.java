package softuni.workshop.constant;

public final class ValidationConstants {

    public final static String USER_NOT_FOUND = "Username not found";
    public final static String USER_EXISTS = "User already exists.";

    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 20;

    public static final String EMAIL_REGEX = "\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,63}\\b";
    public static final String EMAIL_MESSAGE = "Incorrect email.";
    public final static String EMAIL_EXISTS = "Email already exists.";

    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 20;
    public static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*[\\W])[a-zA-Z0-9\\W]{8,30}$";
    public static final String PASSWORD_MESSAGE = "Password is not valid.";
    public static final String PASSWORD_DONT_MATCH = "Password don't match";

    public static final String URL_REGEX = "\\b(([\\w-]+:\\/\\/?|www[.])[^\\s()<>]+(?:\\([\\w\\d]+\\)|([^[:punct:]\\s]|\\/)))";
    public static final String URL_MESSAGE = "Not a valid URL format.";

    public static final String DATE_FORMAT = "d-M-yyyy";
    public static final String DATE_REGEX = "^([0-2][0-9]|(3)[0-1])-(((0)[0-9])|((1)[0-2]))-\\d{4}$";
    public static final String DATE_MESSAGE = "Date must be in format [d-M-yyyy] (31-12-2020).";
}
