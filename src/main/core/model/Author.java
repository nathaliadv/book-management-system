package main.core.model;

import java.time.LocalDate;

public class Author {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String nationality;

    public Author(Long id, String name, LocalDate birthDate, String nationality) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + (birthDate != null ? birthDate : "null") +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
