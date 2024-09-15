package main;

import main.core.model.*;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello!");
        // Create a Library instance
        Library library = new Library();

        Scanner scanner = new Scanner(System.in);

        checkIfIsRegistered(scanner, library);
        interactWithUser(library, scanner);
    }

    static void interactWithUser(Library library, Scanner scanner) throws IOException {

        boolean proceed = true;
        while (proceed) {
            System.out.println("Would you like to see the books available in the library? (Y/N)");
            String response = scanner.nextLine().toLowerCase();
            if(response.equals("y")) {
                manageRequest(library, scanner);
            } else {
                proceed = continueFlow(response);
            }
        }
    }

    static void manageRequest(Library library, Scanner scanner) throws IOException {
        library.printAvailableBook();
        System.out.println("Enter the name of the book you want to borrow (you can copy and paste the name from the list above):");
        String bookName = scanner.nextLine();
        System.out.println("Enter your user number:");
        String userNumber = scanner.nextLine();

        library.createLoan(bookName,  Long.valueOf(userNumber));
    }

    static boolean continueFlow(String response) {
        if (response.equals("n")) {
            System.out.println("Thank you for using our system.");
            return false;
        } else {
            System.out.println("Invalid answer. Please answer with 'Y' or 'N'.");
            return true;
        }
    }

    static void checkIfIsRegistered(Scanner scanner, Library library) throws IOException {
        System.out.println("Do you already have a registration in our system? (Y/N)");
        String response = scanner.nextLine().toLowerCase();

        if(!response.equals("y")) {
            System.out.println("To continue you must register. Do you wish to register? (Y/N)");
            String resp = scanner.nextLine().toLowerCase();
            if(resp.equals("y")) {
                System.out.println("Enter your name: ");
                String name = scanner.nextLine();
                System.out.println("Enter your email: ");
                String email = scanner.nextLine().toLowerCase();
                System.out.println("Enter your phone (format like +[COUNTRY_CODE][STATE_CODE]XXXX-XXXX ): ");
                String phone = scanner.nextLine();
                library.createUser(name, email, phone);
            } else {
                System.out.println("Finishing. Thank you for using our system.");
                System.exit(0);
            }
        }
    }

}
