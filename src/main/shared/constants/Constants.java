package main.shared.constants;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String COMMA_DELIMITER_CSV = ",";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final String pathToFileWithBooks = "/Users/nadantas/projetos/book-management-system/data/books.csv";
    public static final String pathToFileWithAuthors = "/Users/nadantas/projetos/book-management-system/data/authors.csv";
    public static final String pathToFileWithLoans = "/Users/nadantas/projetos/book-management-system/data/loans.csv";
    public static final String pathToFileWithUsers = "/Users/nadantas/projetos/book-management-system/data/users.csv";

    public static final int STANDARD_LOAN_DAYS = 20;
}
