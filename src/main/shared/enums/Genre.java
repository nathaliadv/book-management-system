package main.shared.enums;

public enum Genre {

    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE_FICTION("Science Fiction"),
    FANTASY("Fantasy"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    HORROR("Horror"),
    CHILDRENS("Children's"),
    POETRY("Poetry"),
    DRAMA("Drama"),
    BIOGRAPHY("Biography"),
    DYSTOPIAN("Dystopian"),
    ADVENTURE("Adventure"),
    HISTORICAL("Historical"),
    ;
    private String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Genre fromString(String name) {
        for (Genre genre : Genre.values()) {
            if (genre.getName().equalsIgnoreCase(name)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Unknown genre: " + name);
    }
}
