package com.library.app.utils;

public class Validation {

    public static void requireNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " cannot be empty");
        }
    }

    public static void requireLength(String value, String field, int min, int max) {
        if (value.length() < min || value.length() > max) {
            throw new IllegalArgumentException(
                    field + " length must be between " + min + " and " + max);
        }
    }

    public static void requireValidEmail(String email) {
        if (!EmailValidator.isValid(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static void requireStrongPassword(String password) {
        // At least 8 chars, 1 upper, 1 lower, 1 digit
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters, one uppercase letter, one lowercase letter and one number");
        }
    }
}
