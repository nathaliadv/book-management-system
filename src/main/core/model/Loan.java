package main.core.model;

import main.shared.enums.LoanStatus;

import java.time.LocalDate;

public class Loan {

    private Long id;
    private Book book;
    private User user;
    private LocalDate loanDate; // The date the book was borrowed
    private LocalDate dueDate; // The date the book is due to be returned
    private LocalDate returnDate; // The date the book was actually returned
    private LoanStatus status;

    public Loan(Long id, Book book, User user, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, LoanStatus status) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }
}
