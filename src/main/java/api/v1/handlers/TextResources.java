package api.v1.handlers;

public abstract class TextResources {
    // VALIDATORS
    public static final String EMAIL_EMPTY = "E-mail cannot be empty";
    public static final String EMAIL_NOT_VALID = "E-mail must be valid";
    public static final String PASSWORD_EMPTY = "Password cannot be empty";
    public static final String PASSWORD_NOT_VALID = "Password must be from 6 to 32 characters";
    public static final String X_AXIS_NOT_VALID = "X-axis position must be valid";
    public static final String Y_AXIS_NOT_VALID = "Y-axis position must be valid";

    // ERRORS
    public static final String USER_EMAIL_FOUND = "User with this email already exists";
    public static final String USER_EMAIL_NOT_FOUND = "User with this email not found";
    public static final String USER_NOT_VERIFIED = "Account is not verified";
    public static final String PASSWORD_NOT_CORRECT = "Password is not correct";
    public static final String GAME_OBJECT_NOT_FOUND = "Game object not found";
    public static final String USER_CANNOT_LOOT_OBJECT = "User cannot loot this";
    public static final String LOOT_OBJECT_ON_MAP_NOT_FOUND = "Loot object on map not found";
}
