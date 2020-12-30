package de.jandev.core.utility;

public class LogMessage {

    public static final String USER_NOT_FOUND = "Could not get user '{}' from database. (Maybe it doesn't exist.)";
    public static final String USER_NOT_FOUND_ON_CHAT = "Could not get user '{}' from database while processing chat handler. This should not be possible.";
    public static final String USER_NO_PERMISSION = "User with Id '{}' is not authorized to request this resource.";
    public static final String USER_ID_ALREADY_EXISTS = "User with Id '{}' already exists.";
    public static final String USER_CANNOT_CREATE = "Could not create user with Id '{}'.";
    public static final String USER_CREATED = "User '{}' with Id '{}' created.";
    public static final String COMMAND_NOT_FOUND = "Command '{}' from User with Id '{}' not found.";
    public static final String OAUTH_CALLBACK_FAILED = "OAuth callback failed. Error: '{}', Description: '{}'.";
    public static final String OAUTH_AUTHENTICATION_FAILED_ACCESS_TOKEN = "OAuth authentication failed. Couldn't get access token from code. Code: '{}'.";
    public static final String OAUTH_AUTHENTICATION_FAILED_USER_ERROR = "OAuth authentication failed. Couldn't get user from access token. Access token: '{}'.";
    public static final String JWT_INVALID_OR_EXPIRED = "JWT Token '{}' invalid or expired.";
    public static final String JWT_AUTHENTICATION_ERROR = "JWT authentication encountered an error.";
    public static final String UNHANDLED_EXCEPTION = "Unhandled exception occurred.";
    public static final String REQUEST_FORBIDDEN = "User is not authorized to request this resource.";
    public static final String REQUEST_NOT_READABLE = "Request not readable. Please verify the data.";
    public static final String REQUEST_NOT_ALLOWED = "Method not allowed.";
    public static final String UNHANDLED_EXCEPTION_OUT = "An unhandled exception occurred. Request most likely not processed.";

    private LogMessage() {
    }

}
