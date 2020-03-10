package com.springdata.exercises.services;


import com.springdata.exercises.constants.AgeRestriction;
import com.springdata.exercises.entities.Book;
import com.springdata.exercises.entities.ReducedBook;
import com.springdata.exercises.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class BookServiceImpl implements BookService {

    // inject current repository
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    @Override
    public String[] resultTask1(String input){
        return
        bookRepository
                .findAllByAgeRestriction(AgeRestriction.valueOf(input.trim().toUpperCase()))
                .stream()
                .map(Book::getTitle)
                .toArray(String[]::new);
    }

    @Override
    public String[] resultTask2(int maxCopies){
        return
        bookRepository
                .findAllByCopiesIsLessThan(maxCopies)
                .stream()
                .map(Book::getTitle)
                .toArray(String[]::new);
    }

    @Override
    public String[] resultTask3(BigDecimal lowerThan, BigDecimal higherThan){
        return
        bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(lowerThan, higherThan)
                .stream()
                .map(book -> String.format("%s - $%s",
                        book.getTitle(),
                        book.getPrice().toString())
                )
                .toArray(String[]::new);
    }

    @Override
    public String[] resultTask4(LocalDate before, LocalDate after){
        return
        bookRepository
                .findAllByReleaseDateBeforeOrReleaseDateAfter(before, after)
                .stream()
                .map(Book::getTitle)
                .toArray(String[]::new);
    }

    @Override
    public String[] resultTask5(LocalDate releaseDate){
        return
        bookRepository
                .findAllByReleaseDateBefore(releaseDate)
                .stream()
                .map(book -> String.format("%s %s %s",
                        book.getTitle(),
                        book.getEditionType().toString(),
                        book.getPrice().toString())
                )
                .toArray(String[]::new);
    }

    @Override
    public String[] resultTask7(String input){
        return
        bookRepository
                .findAllByTitleIsLike("%" + input + "%")
                .stream()
                .map(Book::getTitle)
                .toArray(String[]::new);
    }

    @Override
    public String[] resultTask8(String input){
        return
        bookRepository
                .getBooksByAuthorsLastNameLike(input + "%")
                .stream()
                .map(book -> String.format("%s (%s %s)",
                        book.getTitle(),
                        book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName())
                )
                .toArray(String[]::new);
    }

    @Override
    public String resultTask9(int minLength){
        int booksCount = bookRepository.getCountOfBooksWithTitleLengthLongerThan(minLength);
        String result = String.format("There are %d books with longer title than %d symbols%n", booksCount, minLength);
        return result;
    }

    @Override
    public String resultTask11(String title) {
        ReducedBook book = bookRepository.getBookByTitle(title);
        return book == null ? "Not found book." : book.toString();
    }

    @Override
    public String resultTask12(LocalDate releaseDate, int copiesToAdd, DateTimeFormatter formatter) {
        Integer updatedCount = bookRepository.increaseCopiesOfAllBooksReleasedAfter(releaseDate, copiesToAdd);
        return
                String.format("%d books are released after %s, so total of %d book copies were added",
                        updatedCount,
                        releaseDate.format(formatter),
                        updatedCount * copiesToAdd
                );
    }

    @Override
    public String resultTask13(int minCopies) {
        Integer deleted = bookRepository.deleteAllByCopiesLessThan(minCopies);

        return String.format("Deleted books: %d", deleted);
    }

    @Override
    public String resultTask14(String firstName, String lastName) {
        int count = bookRepository.uspCountBooksByAuthorNames(firstName, lastName);

        return String.format("%s %s has written %d books",
                firstName,
                lastName,
                count
        );
    }
}
