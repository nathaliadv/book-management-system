package main.core.model;

import main.shared.enums.Genre;

import java.time.LocalDate;

public class Book {

    private Long id;
    private String title;
    private Author author;
    private Genre genre;
    private boolean isAvailable;
    private LocalDate registrationDate;
    private LocalDate updateDate;
    private int yearOfPublication;

    public Book(Long id, String title, Author author, Genre genre, boolean isAvailable, LocalDate registrationDate,
                LocalDate updateDate, int yearOfPublication) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = isAvailable;
        this.registrationDate = registrationDate;
        this.updateDate = updateDate;
        this.yearOfPublication = yearOfPublication;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.toString() : "null") +
                ", genre=" + genre +
                ", isAvailable=" + isAvailable +
                ", registrationDate=" + (registrationDate != null ? registrationDate : "null") +
                ", updateDate=" + (updateDate != null ? updateDate : "null") +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }
}
