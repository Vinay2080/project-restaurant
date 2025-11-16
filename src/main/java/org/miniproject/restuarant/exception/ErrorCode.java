package org.miniproject.restuarant.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// todo create issue for error code

@Getter
public enum ErrorCode {
    INVALID_JWT_TOKEN("INVALID_JWT_TOKEN", "invalid jwt token = %s", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_TOKEN_TYPE("INVALID_JWT_TOKEN_TYPE", "invalid jwt token type expected 'REFRESH_TOKEN' got %s ", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_EXPIRED("REFRESH TOKEN EXPIRED", "token expired on date = %s", HttpStatus.UNAUTHORIZED),

    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "email already in use, chose different email", HttpStatus.CONFLICT),
    PHONE_ALREADY_EXISTS("PHONE_NUMBER_ALREADY_EXISTS", "phone number already in use, chose different phone number", HttpStatus.CONFLICT),
    CONFIRM_PASSWORD_MISMATCH("CONFIRM_PASSWORD_MISMATCH", "new password does not match with confirm password", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND("USER_NOT_FOUND", "user %s not found", HttpStatus.NOT_FOUND),

    CURRENT_PASSWORD_MISMATCH("CURRENT_PASSWORD_MISMATCH", "password does not match with current password", HttpStatus.FORBIDDEN),
    NO_STATUS_CHANGE("NO_STATUS_CHANGE", "account is already in requested state (enabled/ disabled)", HttpStatus.CONFLICT),

    ERROR_USER_DISABLED("ERROR_USER_DISABLED", "User account is disabled, please activate your account or contact the administrator", HttpStatus.FORBIDDEN),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "username or/and password is incorrect", HttpStatus.UNAUTHORIZED),
    USERNAME_NOT_FOUND("USER_NOT_FOUND", "cannot find user with provided username %s", HttpStatus.NOT_FOUND),
    INTERNAL_EXCEPTION("INTERNAL_EXCEPTION", "an internal error occurred, please try again later or contact the admin", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_ALREADY_EXISTS_FOR_USER("CATEGORY_ALREADY_EXISTS_FOR_USER", "A category with this name already exists for the user", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "role with role-name %s not found", HttpStatus.NOT_FOUND), USER_ALREADY_DEACTIVATED("USER_ALREADY_DEACTIVATED", "user %s is already deactivated", HttpStatus.CONFLICT),
    USER_ALREADY_ACTIVE("USER_ALREADY_ACTIVE", "user %s is already activated", HttpStatus.CONFLICT),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Staff not found with ID: %s", HttpStatus.NOT_FOUND),
    DUPLICATE_RESOURCE("DUPLICATE_RESOURCE", "Salary record already exists for the staff in the specified period", HttpStatus.CONFLICT),
    VALIDATION_ERROR("VALIDATION_ERROR", "Start date must be before or equal to end date", HttpStatus.BAD_REQUEST),
    INVALID_OPERATION("INVALID_OPERATION", "Salary is already marked as paid", HttpStatus.BAD_REQUEST), RESOURCE_ALREADY_EXISTS("RESOURCE_ALREADY_EXISTS", "Staff with employee ID %S already exists", HttpStatus.CONFLICT);
    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(
            final String code,
            final String defaultMessage,
            final HttpStatus httpStatus
    ) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }
}
