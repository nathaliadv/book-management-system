package main.shared.enums;

public enum LoanStatus {
    ACTIVE("active"),        // The loan is currently active
    RETURNED("returned"),    // The book has been returned
    OVERDUE("overdue"),      // The book is overdue for return
    CANCELLED("cancelled"); // The loan has been cancelled

    private final String name;

    LoanStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LoanStatus fromString(String name) {
        for (LoanStatus status : LoanStatus.values()) {
            if (status.getName().equalsIgnoreCase(name)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown LoanStatus: " + name);
    }
}
