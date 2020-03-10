package com.springdata.exercises.controllers;

import com.springdata.exercises.services.AuthorService;
import com.springdata.exercises.services.BookService;
import com.springdata.exercises.services.CategoryService;
import com.springdata.exercises.services.DatabaseService;
import com.springdata.exercises.utils.UserInput;
import com.springdata.exercises.utils.UserOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final DatabaseService databaseService;
    private final UserInput userInput;
    private final UserOutput userOutput;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService, DatabaseService databaseService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.databaseService = databaseService;
        userInput = new UserInput();
        userOutput = new UserOutput();
    }

    @Override
    public void run(String... args) throws Exception {

        // seed data
        databaseService.seed();

        userOutput.print("***");
        userOutput.print("***");
        userOutput.print("***");
        userOutput.print("Exercises: Spring Data Advanced Quering");
        userOutput.print("init database...");

        int queryNumber = 1;

        while (queryNumber != 0){
            try {
                userOutput.print("->");
                String number = userInput.get("Enter query number [1-14] | Exit(0) |", String.valueOf(queryNumber));
                queryNumber = Integer.parseInt(number);

                if (queryNumber > 0 && queryNumber < 15) {
                    switch (queryNumber){
                        case 1 : printTask1(); break;
                        case 2 : printTask2(); break;
                        case 3 : printTask3(); break;
                        case 4 : printTask4(); break;
                        case 5 : printTask5(); break;
                        case 6 : printTask6(); break;
                        case 7 : printTask7(); break;
                        case 8 : printTask8(); break;
                        case 9 : printTask9(); break;
                        case 10 : printTask10(); break;
                        case 11 : printTask11(); break;
                        case 12 : printTask12(); break;
                        case 13 : printTask13(); break;
                        case 14 : printTask14(); break;
                    }
                } else {
                    userOutput.print("Invalid number of query.");
                }
            } catch (NumberFormatException e){
                userOutput.print("Bad number.");
            }
        }

    }

    private void printTask1(){
        String description = "1. Books Titles by Age Restriction";
        String print = "Enter age restriction";
        String defaultValue = "miNor";

        userOutput.print(description);
        userOutput.print(bookService.resultTask1(userInput.get(print, defaultValue)));
    }

    private void printTask2(){
        String description = "2. Golden Books";
        int MAX_COPIES = 5000;

        userOutput.print(description);
        userOutput.print(bookService.resultTask2(MAX_COPIES));
    }

    private void printTask3(){
        String description = "3. Books by Price";
        BigDecimal PRICE_LOWER_THAN = new BigDecimal(5);
        BigDecimal PRICE_HIGHER_THAN = new BigDecimal(40);

        userOutput.print(description);
        userOutput.print(bookService.resultTask3(PRICE_LOWER_THAN, PRICE_HIGHER_THAN));
    }

    private void printTask4(){
        String description = "4. Not Released Books";
        String print = "Enter year";
        String defaultValue = "2000";

        String input = userInput.get(print, defaultValue);
        int year = Integer.parseInt(input);

        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);

        userOutput.print(description);
        userOutput.print(bookService.resultTask4(before, after));
    }

    private void printTask5(){
        String description = "5. Books Released Before Date";
        String INPUT_DATE_FORMAT = "dd-MM-yyyy";
        String print = String.format("Enter date in the format %s", INPUT_DATE_FORMAT);
        String defaultValue = "12-04-1992";
        String input = userInput.get(print, defaultValue);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
        LocalDate releaseDate = LocalDate.parse(input, formatter);

        userOutput.print(description);
        userOutput.print(bookService.resultTask5(releaseDate));
    }

    private void printTask6(){
        String description = "6. Authors Search";
        String print = "Enter end characters";
        String defaultValue = "e";

        userOutput.print(description);
        userOutput.print(authorService.resultTask6(userInput.get(print, defaultValue)));
    }

    private void printTask7(){
        String description = "7. Books Search";
        String print = "Enter title characters";
        String defaultValue = "sK";

        userOutput.print(description);
        userOutput.print(bookService.resultTask7(userInput.get(print, defaultValue)));
    }

    private void printTask8(){
        String description = "8. Book Titles Search";
        String print = "Enter authors last name";
        String defaultValue = "Ric";

        userOutput.print(description);
        userOutput.print(bookService.resultTask8(userInput.get(print, defaultValue)));
    }

    private void printTask9(){
        String description = "9. Count Books";
        String print = "Enter minimal title length";
        String defaultValue = "12";

        try {
            userOutput.print(description);
            int minLength = Integer.parseInt(userInput.get(print, defaultValue));
            userOutput.print(bookService.resultTask9(minLength));

        }catch (NumberFormatException e){
            userOutput.print("Bad input.");
        }
    }

    private void printTask10(){
        String description = "10. Total Book Copies";

        userOutput.print(description);
        userOutput.print(authorService.resultTask10());
    }

    private void printTask11(){
        String description = "11. Reduced Book";
        String print = "Enter book title";
        String defaultValue = "Things Fall Apart";

        userOutput.print(description);
        userOutput.print(bookService.resultTask11(userInput.get(print, defaultValue)));
    }

    private void printTask12(){
        String description = "12. *Increase Book Copies";
        String print = "Enter date in the format dd MMM yyyy";
        String defaultDate = "12 Oct 2005";
        String defaultNumber = "100";
        String INPUT_DATE_FORMAT = "dd MMM yyyy";

        userOutput.print(description);
        String input = userInput.get(print, defaultDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
        LocalDate releaseDate = LocalDate.parse(input, formatter);

        int copiesToAdd = Integer.parseInt(userInput.get("Enter number", defaultNumber));

        userOutput.print(bookService.resultTask12(releaseDate, copiesToAdd, formatter));
    }

    private void printTask13(){
        String description = "13.*Remove Books, which copies are lower than a given number.";
        String print = "Enter number of copies";
        String defaultValue = "7000";

        userOutput.print(description);
        String input = userInput.get(print, defaultValue);

        try {
            int minCopies = Integer.parseInt(input);
            userOutput.print(bookService.resultTask13(minCopies));

        }catch (NumberFormatException e){
            System.out.println("Bad input");
        }
    }

    private void printTask14(){
        String description = "14.*Stored Procedure";
        String print = "Enter an author’s first and last name";
        String defaultValue = "Amanda Rice";

        userOutput.print(description);
        String input = userInput.get(print, defaultValue);

        String[] data = input.split("\\s+");
        String firstName = data[0];
        String lastName = data[1];

        userOutput.print(bookService.resultTask14(firstName, lastName));
    }
}
