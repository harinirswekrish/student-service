package com.mini.school.erp.student_service.exception.errormessages;

public class ErrorMessages {
    public static final String TOKEN_EXPIRED = "Token has expired, please log in again.";
    public static final String INVALID_TOKEN = "Invalid or malformed token.";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access.";
    public static final String REDIS_TOKEN_VALIDATE = "Token not found in Redis (maybe expired or user logged out)";
    public static final String TOKEN_MISMATCH = "Token mismatch (another login might have replaced this one)";
}
