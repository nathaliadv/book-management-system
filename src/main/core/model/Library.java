package main.core.model;

import main.shared.enums.Genre;
import main.shared.enums.LoanStatus;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static main.shared.constants.Constants.*;

public class Library {

    List<Book> books;
    List<Author> authors;
    List<Loan> loans;
    List<User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.authors = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.users = new ArrayList<>();

        loadAuthorsFromCSV(pathToFileWithAuthors);
        loadBooksFromCSV(pathToFileWithBooks);
        loadUsersFromCSV(pathToFileWithUsers);
        loadLoansFromCSV(pathToFileWithLoans);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<User> getUsers() {
        return users;
    }


    public void addNewBook(String authorName, String nationality, String bookTitle, Genre genre, int yearOfPublication,
                           LocalDate birthDate) throws IOException {
        Author author = this.authors.stream()
                .filter(auth -> auth.getName().equalsIgnoreCase(authorName))
                .findFirst()
                .orElse(null);
        Long idAuthor;
        if (author != null) {
            idAuthor = author.getId();
        } else {
            idAuthor = generateIdNewAuthor();
            author = new Author(idAuthor, authorName, birthDate, nationality);
            authors.add(author);
            saveAuthorsToCSV();
        }

        Book book = new Book(generateIdNewBook(), bookTitle, author, genre, true, LocalDate.now(), LocalDate.now(), yearOfPublication);

        System.out.println(author.toString());
        System.out.println(book.toString());

        books.add(book);
        saveBooksToCSV();

        System.out.println(String.format("The book %s by author %s has been registered successfully!", bookTitle, authorName));
        System.out.println();
    }

    public void printBooks() {
        if (getBooks().isEmpty()) {
            System.out.println("There are no books available at the moment.");
        } else {
            System.out.println("\nBooks:");
            for (Book book : getBooks()) {
                System.out.println(book.getId() + " - " + book.getTitle() + ", " + book.getAuthor().getName() + ", "
                        + book.getGenre().getName() + ", " + book.isAvailable() + ", " + book.getRegistrationDate()
                        + ", " + book.getUpdateDate() + ", " + book.getYearOfPublication());
            }
        }

    }

    public void printAuthors() {
        if (getAuthors().isEmpty()) {
            System.out.println("There are no authors registered in our system.");
        } else {
            System.out.println("Authors:");
            for (Author author : getAuthors()) {
                System.out.println(author.getId() + " - " + author.getName() + ", " + author.getBirthDate() + ", "
                        + author.getNationality());
            }
        }
    }

    public void printLoans() {
        if(getLoans().isEmpty()) {
            System.out.println("There are no loans registered in our system.");
        } else {
            System.out.println("\nLoans:");
            for (Loan loan : getLoans()) {
                System.out.println(loan.getId() + " - Book ID: " + loan.getBook().getId() + ", User ID: "
                        + loan.getUser().getId() + ", Loan Date: " + loan.getLoanDate() + ", Return Date: "
                        + loan.getReturnDate());
            }
        }
    }

    public void printUsers() {
        if(users.isEmpty()) {
            System.out.println("There are no users registered in our system.");
        } else {
            System.out.println("\nUsers:");
            for (User user : getUsers()) {
                System.out.println(user.getId() + " - " + user.getName() + ", " + user.getEmail() + ", "
                        + user.getPhoneNumber());
            }
        }
    }

    public void printAvailableBook() {
        List<Book> availableBooks = generateListOfAvailableBooks();
        if(availableBooks.isEmpty()) {
            System.out.println("There are no books available at the moment");
        } else {
            System.out.println("\nBooks Available:");
            for (Book book : generateListOfAvailableBooks()) {
                System.out.println(book.getId() + " - " + book.getTitle() + ", " + book.getAuthor().getName() + " - "
                        + book.getGenre().getName() + " - " + book.getYearOfPublication());
            }
        }
    }

    private List<Book> generateListOfAvailableBooks() {
        return books.stream()
                .filter(book -> book.isAvailable() == true)
                .collect(Collectors.toList());
    }

    public void createLoan(String bookTitle, Long userId) throws IOException {
        List<Book> booksWithSameTitle = getBooks().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle))
                .collect(Collectors.toList());

        if (booksWithSameTitle == null || booksWithSameTitle.isEmpty()) {
            System.out.println("Unfortunately we don't have this book in our library.");
            return;
        }

        Book bookAvailableWithSameTitle = booksWithSameTitle.stream()
                .filter(book -> book.isAvailable() == true)
                .findFirst()
                .orElse(null);
        if (bookAvailableWithSameTitle == null) {
            System.out.println("Unfortunately this book is not available.");
        } else {
            User registeredUser = getUsers().stream()
                    .filter(user -> user.getId() == userId)
                    .findFirst()
                    .orElse(null);
            if (registeredUser == null) {
                System.out.println("You are currently not registered with the library. Please register in order to borrow a book.");
                System.exit(0);
            }

            LocalDate registrationDate = LocalDate.now();
            LocalDate dueDate = registrationDate.plusDays(STANDARD_LOAN_DAYS);
            Loan newLoan = new Loan(generateIdNewLoad(), bookAvailableWithSameTitle, registeredUser, registrationDate,
                    dueDate, null, LoanStatus.ACTIVE);
            this.loans.add(newLoan);
            saveLoansToCSV();
            updateBookStatus(bookAvailableWithSameTitle, false);
            System.out.println(String.format("Your loan has been approved! You have until day %s to return it.", dueDate));
        }
    }

    public void createUser(String name, String email, String phoneNumber) throws IOException {
        User registeredUser = this.users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email) || user.getPhoneNumber().equalsIgnoreCase(phoneNumber))
                .findFirst()
                .orElse(null);
        if (registeredUser != null) {
            System.out.println("There is already a registered user with that email or phone number");
            System.exit(0);
            return;
        } else {
            Long idNewUser = generateIdNewUser();
            User newUser = new User(idNewUser, name, email, phoneNumber);
            this.users.add(newUser);
            saveUsersToCSV();
            System.out.println(String.format("Your registration number is %s. You must provide this number to request a book loan.", idNewUser));
        }
    }

    private void updateBookStatus(Book chosenBook, boolean isAvailable) throws IOException {
        this.books.stream().filter(book -> book.getId().equals(chosenBook.getId()))
                .findFirst()
                .ifPresent(book -> {
                    book.setAvailable(isAvailable);
                    book.setUpdateDate(LocalDate.now());
                });
        saveBooksToCSV();
    }

    private void saveAuthorsToCSV() throws IOException {
        List<Author> authors = getAuthors();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFileWithAuthors))) {
            writer.write("id,name,birthdate,nationality\n");
            for (Author author : authors) {
                String authorLine = String.join(",",
                        author.getId().toString(),
                        author.getName().toString(),
                        author.getBirthDate() != null ? author.getBirthDate().toString() : "null",
                        author.getNationality().toString());
                writer.write(authorLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException(e.toString());
        }
    }

    private void saveBooksToCSV() throws IOException {
        List<Book> books = getBooks();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFileWithBooks))) {
            writer.write("id,title,author_id,genre,is_available,registration_date,update_date,year_of_publication\n");
            for (Book book : books) {
                String bookLine = String.join(",",
                        book.getId().toString(),
                        book.getTitle(),
                        book.getAuthor().getId().toString(),
                        book.getGenre().getName().toString(),
                        Boolean.toString(book.isAvailable()),
                        book.getRegistrationDate() != null? book.getRegistrationDate().toString() : "null",
                        book.getUpdateDate() != null ? book.getUpdateDate().toString() : "null",
                        Integer.toString(book.getYearOfPublication())
                );
                writer.write(bookLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException(e.toString());
        }
    }

    private void saveLoansToCSV() throws IOException {
        List<Loan> loans = getLoans();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFileWithLoans))) {
            writer.write("id,book_id,user_id,loan_date,due_date,return_date,status\n");
            for (Loan loan : loans) {
                String loanLine = String.join(",",
                        loan.getId().toString(),
                        loan.getBook().getId().toString(),
                        loan.getUser().getId().toString(),
                        loan.getLoanDate() != null ? loan.getLoanDate().toString() : "null",
                        loan.getDueDate() != null ? loan.getDueDate().toString() : "null",
                        loan.getReturnDate() != null ? loan.getReturnDate().toString() : "null",
                        loan.getStatus().getName().toString()
                );
                writer.write(loanLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException(e.toString());
        }
    }

    private void saveUsersToCSV() throws IOException {
        List<User> users = getUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFileWithUsers))) {
            writer.write("id,name,email,phone_number\n");
            for (User user : users) {
                String userLine = String.join(",",
                        user.getId().toString(),
                        user.getName().toString(),
                        user.getEmail().toString(),
                        user.getPhoneNumber().toString()
                );
                writer.write(userLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException(e.toString());
        }
    }

    private void loadAuthorsFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip the header row

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Author author = new Author(
                        Long.parseLong(fields[0]), // ID
                        fields[1], // Name
                        parseDate(fields[2]), // BirthDate
                        fields[3] // Nationality
                );
                authors.add(author);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooksFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(COMMA_DELIMITER_CSV);
                Book book = new Book(
                        Long.parseLong(fields[0]), // ID
                        fields[1], // Title
                        getAuthorById(Long.parseLong(fields[2])), // Author
                        Genre.fromString(fields[3]), // Genre
                        Boolean.parseBoolean(fields[4]), // isAvailable
                        parseDate(fields[5]), // registrationDate
                        parseDate(fields[6]), // updateDate
                        Integer.parseInt(fields[7]) // yearOfPublication
                );
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsersFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip the header row

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                User user = new User(
                        Long.parseLong(fields[0]), // ID
                        fields[1], // Name
                        fields[2], // Email
                        fields[3] // Phone
                );
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLoansFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip the header row

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Loan loan = new Loan(
                        Long.parseLong(fields[0]), // ID
                        getBookById(Long.parseLong(fields[1])), // Book
                        getUserById(Long.parseLong(fields[2])), // User
                        parseDate(fields[3]), // Loan Date
                        parseDate(fields[4]), // Due Date
                        parseDate(fields[5]), // Returned Date
                        LoanStatus.fromString(fields[6]) // Status
                );
                loans.add(loan);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Author getAuthorById(Long id) {
        return authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private Book getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private LocalDate parseDate(String date) {
        if ("null".equals(date) || date == null || "".equals(date)) {
            return null;
        }
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    private Long generateIdNewAuthor() {
        if (!this.authors.isEmpty()) {
            return authors.get(authors.size() - 1).getId() + 1;
        } else {
            return Long.valueOf("1");
        }
    }

    private Long generateIdNewBook() {
        if (!this.books.isEmpty()) {
            return books.get(books.size() - 1).getId() + 1;
        } else {
            return Long.valueOf("1");
        }
    }

    private Long generateIdNewLoad() {
        if (!this.loans.isEmpty()) {
            return loans.get(loans.size() - 1).getId() + 1;
        } else {
            return Long.valueOf("1");
        }
    }

    private Long generateIdNewUser() {
        if (!this.users.isEmpty()) {
            return users.get(users.size() - 1).getId() + 1;
        } else {
            return Long.valueOf("1");
        }
    }
}
