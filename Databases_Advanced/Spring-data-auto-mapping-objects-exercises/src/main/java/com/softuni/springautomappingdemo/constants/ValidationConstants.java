package com.softuni.springautomappingdemo.constants;

public final class ValidationConstants {

    // javax.validation -> user
    public static final String USER_EMAIL_REGEX = "\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,63}\\b";
    public static final String USER_EMAIL_MESSAGE = "Incorrect email.";

    public static final String USER_NAME_NOT_NULL_MESSAGE = "Full name must not be null";

    public static final String USER_PASSWORD_REGEX = "[A-Z]+[a-z]+[0-9]+";
    public static final String USER_PASSWORD_MESSAGE = "Password is not valid";
    public static final String USER_PASSWORD_LENGTH_MESSAGE = "Password length is not valid";

    // Custom PasswordValidator
    public static final int USER_PASSWORD_MIN_LENGTH = 6;
    public static final int USER_PASSWORD_MAX_LENGTH = 30;
    public static final boolean USER_PASSWORD_SHOULD_CONTAIN_LOWER_CASE = true;
    public static final boolean USER_PASSWORD_SHOULD_CONTAIN_UPPER_CASE = true;
    public static final boolean USER_PASSWORD_SHOULD_CONTAIN_DIGIT = true;
    public static final boolean USER_PASSWORD_CAN_BE_OMITTED = false;

    public static final String PASSWORD_TOO_SIMPLE = "Password too simple!";
    public static final String PASSWORD_TOO_SHORT = "Password too short!";
    public static final String PASSWORD_TOO_LONG = "Password too long!";
    public static final String PASSWORD_CANNOT_BE_EMPTY = "Password cannot be empty!";
    public static final String PASSWORD_SHOULD_CONTAIN_LOWERCASE_LETTER = "Password should contain lowercase letter!";
    public static final String PASSWORD_SHOULD_CONTAIN_UPPERCASE_LETTER = "Password should contain uppercase letter!";
    public static final String PASSWORD_SHOULD_CONTAIN_DIGIT = "Password should contain digit!";

    // javax.validation -> game
    public static final String GAME_TITLE_REGEX = "[A-Z].*";
    public static final String GAME_TITLE_BEGIN_WITH_UPPERCASE = "Title – has to begin with an uppercase letter";
    public static final String GAME_TITLE_LENGTH = "Title – must have length between 3 and 100 symbols";

    public static final String GAME_PRICE_MESSAGE = "Price – must be a positive number.";
    public static final String GAME_SIZE_MESSAGE = "Size – must be a positive number.";

    public static final String GAME_TRAILER_REGEX = "[a-zA-Z0-9]{11}";
    public static final String GAME_TRAILER_MESSAGE = "Trailer – is a string of exactly 11 characters.";

    public static final String GAME_URL_REGEX = "\\b(([\\w-]+:\\/\\/?|www[.])[^\\s()<>]+(?:\\([\\w\\d]+\\)|([^[:punct:]\\s]|\\/)))";
    public static final String GAME_URL_MESSAGE = "Thumbnail URL – it should be a plain text starting with http://, https://:";

    public static final String GAME_DESCRIPTION_MESSAGE = "Description – must be at least 20 symbol";

    public static final String GAME_DATE_REGEX = "^([0-2][0-9]|(3)[0-1])-(((0)[0-9])|((1)[0-2]))-\\d{4}$";
    public static final String GAME_DATE_MESSAGE = "Release date - must be in format [d-M-yyyy]";
}
