package com.springdata.exercises.controllers;

import com.springdata.exercises.constants.AgeRestriction;
import com.springdata.exercises.constants.EditionType;
import com.springdata.exercises.services.AuthorService;
import com.springdata.exercises.services.BookService;
import com.springdata.exercises.services.CategoryService;
import com.springdata.exercises.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final DatabaseService databaseService;
    private final Scanner scanner;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService, DatabaseService databaseService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.databaseService = databaseService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        // seed data
        databaseService.seed();

        System.out.println("***");
        System.out.println("***");
        System.out.println("***");
        System.out.println("Exercises: Spring Data Advanced Quering");
        System.out.println("init database...");

        try (Scanner scanner = new Scanner(System.in)) {

            int queryNumber = 1;

            while (queryNumber != 0){

                System.out.println("->");
                try {
                    String input = getInput("Enter query number [1-14] | Exit(0) |", String.valueOf(queryNumber));
                    queryNumber = Integer.parseInt(input);

                    if (queryNumber > 0 && queryNumber < 15) {
                        switch (queryNumber){
                            case 1 : this.booksTitlesByAgeRestrictionEx(); break;
                            case 2 : this.goldenBooksEx(); break;
                            case 3 : this.booksByPriceEx(); break;
                            case 4 : this.notReleasedBooksEx(); break;
                            case 5 : this.booksReleasedBeforeDateEx(); break;
                            case 6 : this.authorsSearchEx(); break;
                            case 7 :  break;
                            case 8 : this.bookTitlesSearchEx(); break;
                            case 9 :  break;
                            case 10 :  break;
                            case 11 :  break;
                            case 12 :  break;
                            case 13 :  break;
                            case 14 :  break;
                        }
                    } else {
                        System.out.println("Invalid number of query.");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Bad number.");
                }
            }
        }
    }

    private String getInput(String print, String defaultValue){
        if (!print.isBlank()){
            System.out.printf("%s%s",
                    print,
                    defaultValue == null ? "%n" : String.format(" <%s> :%n", defaultValue));
        }
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue == null ? "" : defaultValue : input;
    }

    private void booksTitlesByAgeRestrictionEx(){
        System.out.println("1. Books Titles by Age Restriction");
        String input = getInput("Enter age restriction", "miNor");

        bookService
                .getAllBooksAgeRestriction(
                        AgeRestriction.valueOf(input.trim().toUpperCase())
                )
                .forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }

    private void goldenBooksEx(){
        System.out.println("2. Golden Books");
        int MAX_COPIES = 5000;

        bookService
                .getAllBooksWithCopiesLessThan(MAX_COPIES)
                .forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }

    private void booksByPriceEx(){
        System.out.println("3. Books by Price");
        BigDecimal PRICE_LOWER_THAN = new BigDecimal(5);
        BigDecimal PRICE_HIGHER_THAN = new BigDecimal(40);

        bookService
                .getAllBooksWithPriceNotBetweenExclusive(PRICE_LOWER_THAN, PRICE_HIGHER_THAN)
                .forEach(book -> System.out.printf("%s - $%s%n",
                        book.getTitle(),
                        book.getPrice().toString()
                ));
    }

    private void notReleasedBooksEx(){
        System.out.println("4. Not Released Books");
        String input = getInput("Enter year", "2000");
        int year = Integer.parseInt(input);

        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);

        bookService
                .getAllBooksNotReleasedBetweenDates(before, after)
                .forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }

    private void booksReleasedBeforeDateEx(){
        System.out.println("5. Books Released Before Date");
        String INPUT_DATE_FORMAT = "dd-MM-yyyy";

        String print = String.format("Enter date in the format %s", INPUT_DATE_FORMAT);
        String input = getInput(print, "12-04-1992");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
        LocalDate releaseDate = LocalDate.parse(input, formatter);

        bookService
                .getAllBooksBefore(releaseDate)
                .forEach(book -> System.out.printf("%s %s %s%n",
                        book.getTitle(),
                        book.getEditionType().toString(),
                        book.getPrice().toString()
                ));
    }

    private void authorsSearchEx(){
        System.out.println("6. Authors Search");
        String input = getInput("Enter end characters", "e");

        authorService
                .getAllAuthorsWithFirstNameLike("%" + input)
                .forEach(author -> System.out.printf("%s %s%n",
                        author.getFirstName(),
                        author.getLastName()
                ));
    }

    private void bookTitlesSearchEx(){
        System.out.println("8. Book Titles Search");
        String input = getInput("Enter authors last name", "Ric");

        bookService
                .getBooksByAuthorsLastNameLike(input + "%")
                .forEach(book -> System.out.printf("%s (%s %s)%n",
                        book.getTitle(),
                        book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()
                ));
    }

}
