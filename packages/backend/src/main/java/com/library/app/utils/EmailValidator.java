package com.library.app.utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmailValidator {

    // RFC 5322 compliant (simplified but strong)
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValid(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            return false;
        }

        // Extra domain validation (no double dots, no leading/trailing dots)
        String domain = email.substring(email.indexOf("@") + 1);
        return !domain.startsWith(".")
                && !domain.endsWith(".")
                && !domain.contains("..");
    }

    public static void main(String[] args) {
        String[] emails = {
                "test@example.com",
                "user.name@domain.co",
                "invalid@domain..com",
                "noatsymbol.com",
                "bad@.domain.com"
        };

        for (String email : emails) {
            System.out.println(email + " -> " + isValid(email));
        }
    }
}
