package alararestaurant.constant;

public final class ValidationConstants {

    public static final String USER_EMAIL_REGEX = "\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,63}\\b";
    public static final String USER_EMAIL_MESSAGE = "Incorrect email.";

    public static final String USER_PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*[\\W])[a-zA-Z0-9\\W]{8,30}$";
    public static final String USER_PASSWORD_MESSAGE = "Password is not valid";

    public static final String URL_REGEX = "\\b(([\\w-]+:\\/\\/?|www[.])[^\\s()<>]+(?:\\([\\w\\d]+\\)|([^[:punct:]\\s]|\\/)))";
    public static final String URL_MESSAGE = "Thumbnail URL – it should be a plain text starting with http://, https://:";

    public static final String DATE_FORMAT = "d-M-yyyy";
    public static final String DATE_REGEX = "^([0-2][0-9]|(3)[0-1])-(((0)[0-9])|((1)[0-2]))-\\d{4}$";
    public static final String DATE_MESSAGE = "Date must be in format [d-M-yyyy] (31-12-2020)";
}
