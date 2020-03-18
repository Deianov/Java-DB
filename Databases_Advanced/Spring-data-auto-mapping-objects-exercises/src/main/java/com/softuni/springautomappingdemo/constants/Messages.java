package com.softuni.springautomappingdemo.constants;

public final class Messages {

    // Input
    public static final String BAD_INPUT = "Bad input.";
    public static final String BAD_INPUT_LINE = "Bad input: %s";

    // User
    public static final String PASSWORD_DONT_MATCH = "Password don't match";
    public static final String USER_REGISTERED = "%s was registered";
    public static final String USER_ALREADY_REGISTERED = "Email <%s> already registered";

    public static final String USER_LOGGED = "Successfully logged in %s";
    public static final String USER_ALREADY_LOGGED = "User <%s> already logged";
    public static final String USER_LOGGING_ERROR = "Incorrect username / password";
    public static final String USER_NOT_FOUND = "Not found user: %s";

    public static final String USER_LOGOUT = "User %s successfully logged out";
    public static final String USER_LOGOUT_ERROR = "Cannot log out. No user was logged in.";

    public static final String ADMIN_REQUIRED = "Only administrators are authorised for this action!";
    public static final String USER_REQUIRED = "This action as valid only for logged in users!";

    // Game
    public static final String GAME_EXIST = "Game already exist";
    public static final String GAME_ADDED = "Added %s";
    public static final String GAME_NOT_FOUND = "Not found game.";
    public static final String GAME_EDITED = "Edited %s";
    public static final String GAME_DELETED = "Deleted %s";

    // Store
    public static final String GAME_EXIST_IN_CART = "Game already exist in cart.";
    public static final String GAME_ALREADY_OWNED = "Game already owned by user.";
    public static final String GAME_ADDED_TO_CARD = "%s added to cart.";
    public static final String GAME_REMOVED_FROM_CARD = "%s removed from cart.";
    public static final String GAME_NOT_FOUND_IN_CARD = "%s not found in cart.";
    public static final String USER_BOUGHT_GAMES = "Successfully bought games:%n -%s";
    public static final String NOT_FOUND_GAMES = "Not found games.";
    public static final String NOT_FOUND_OWNED_GAMES = "Not found owned games.";
}
