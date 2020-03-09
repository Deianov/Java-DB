package com.springdata.exercises.services;

import com.springdata.exercises.constants.AgeRestriction;
import com.springdata.exercises.entities.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    List<Book> getAllBooksAfter(LocalDate releaseDate);
    List<Book> getAllBooksBefore(LocalDate releaseDate);
    List<Book> getAllBooksAgeRestriction(AgeRestriction ageRestriction);
    List<Book> getAllBooksWithCopiesLessThan(int copies);
    List<Book> getAllBooksWithPriceNotBetweenExclusive(BigDecimal lowerThan, BigDecimal higherThan);
    List<Book> getAllBooksNotReleasedBetweenDates(LocalDate before, LocalDate after);

    List<Book> getBooksByAuthorsLastNameLike(String lastName);
}
