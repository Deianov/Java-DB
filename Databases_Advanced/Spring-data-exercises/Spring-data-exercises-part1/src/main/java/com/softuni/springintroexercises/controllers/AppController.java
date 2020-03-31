package com.softuni.springintroexercises.controllers;

import com.softuni.springintroexercises.entities.Author;
import com.softuni.springintroexercises.entities.Book;
import com.softuni.springintroexercises.services.AuthorService;
import com.softuni.springintroexercises.services.BookService;
import com.softuni.springintroexercises.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Scanner;


@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;


    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @Override
    public void run(String... args) throws Exception {

        // seed data
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();

        System.out.println("***");
        System.out.println("Bookshop System");
        System.out.println("Init database.");


        try (Scanner scanner = new Scanner(System.in)) {

            int queryNumber = 1;

            while (queryNumber != 0){

                System.out.println("->");
                System.out.println("Enter query number [1-4] | Exit(0) : ");
                try {
                    queryNumber = Integer.parseInt(scanner.nextLine());

                    if (queryNumber > 0 && queryNumber < 5) {
                        switch (queryNumber){
                            case 1 :
                                this.getAllBooksAfter2000();
                                break;
                            case 2 :
                                this.getAllAuthorsWithBookBefore1990();
                                break;
                            case 3 :
                                this.findAllAuthorsByCountOfBooks();
                                break;
                            case 4 :
                                this.findAllBooksByAuthorOrdered();
                                break;
                        }
                    }
                } catch (NumberFormatException e){
                    System.out.println("Bad number.");
                }
            }
        }
    }


    private void getAllBooksAfter2000(){
        System.out.println("1. Get all books after the year 2000. Print only their titles.");
        bookService
                .getAllBooksAfter(LocalDate.of(2000,12,31))
                .forEach(book -> System.out.printf("%s%n", book.getTitle()));
    }

    private void getAllAuthorsWithBookBefore1990(){
        System.out.println("2. Get all authors with at least one book with release date before 1990. Print their first name and last name.");
        bookService
                .getAllBooksBefore(LocalDate.of(1990,1,1))
                .stream()
                .map(Book::getAuthor)
                .distinct()
                .sorted(Comparator.comparing(Author::getFirstName))
                .forEach(author -> System.out.printf("%s %s%n",
                        author.getFirstName(),
                        author.getLastName()
                ));
    }

    private void findAllAuthorsByCountOfBooks(){
        System.out.println("3. Get all authors, ordered by the number of their books (descending). Print their first name, last name and book count.");
        authorService
                .findAllAuthorsByCountOfBooks()
                .forEach(author -> System.out.printf("%s %s %d%n",
                        author.getFirstName(),
                        author.getLastName(),
                        author.getBooks().size()
                ));
    }

    private void findAllBooksByAuthorOrdered(){
        System.out.println("4. Get all books from author George Powell, ordered by their release date (descending), then by book title (ascending). Print the book's title, release date and copies.");

        String FIRST_NAME = "George";
        String LAST_NAME = "Powell";

        Author author = authorService.findAuthorByNames(FIRST_NAME, LAST_NAME);
        if(author != null){
            bookService
                    .findAllBooksByAuthorOrderByReleaseDateDescAndTitleAsc(author)
                    .forEach(book -> System.out.printf("%nTitle: %s%nRelease date: %s%nCopies: %d%n",
                            book.getTitle(),
                            book.getReleaseDate().toString(),
                            book.getCopies())
                    );
        } else {
            System.out.printf("Not found author %s %s%n", FIRST_NAME, LAST_NAME);
        }
    }
}
