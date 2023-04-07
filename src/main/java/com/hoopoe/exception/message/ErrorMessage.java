package com.hoopoe.exception.message;

public abstract class ErrorMessage {

    public static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource with %id not found";

    public static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";

    public static final String JWTTOKEN_ERROR_MESSAGE = "Jwt token has an error";

    public static final String EMAIL_ALREADY_EXIST_MESSAGE = "Email: %s already exist";

    public static final String ROLE_NOT_FOUND_EXCEPTION = "Role %s not found";

    public static final String PRINCIPAL_NOT_FOUND_MESSAGE = "User not found";

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You don't have any permission to change this data";

    public static final String PASSWORD_NOT_MATCHED = "Your password are not matvhed";

    public static final String IMAGE_NOT_FOUND_MESSSAGE = "Image with {id} not found";

    public static final String IMAGE_USED_MESSAGE = "Image file used by other product";
}
