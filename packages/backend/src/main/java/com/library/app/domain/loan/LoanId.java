package com.library.app.domain.loan;

import java.util.UUID;

public record LoanId(UUID value) {

    public LoanId {
        if (value == null) {
            throw new IllegalArgumentException("LoanId cannot be null");
        }
    }

    public static LoanId random() {
        return new LoanId(UUID.randomUUID());
    }
}
