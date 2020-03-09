package com.springdata.exercises.services;


import com.springdata.exercises.constants.AgeRestriction;
import com.springdata.exercises.entities.Book;
import com.springdata.exercises.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    // inject current repository
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooksAfter(LocalDate releaseDate) {
        return bookRepository.findAllByReleaseDateAfter(releaseDate);
    }

    @Override
    public List<Book> getAllBooksBefore(LocalDate releaseDate) {
        return bookRepository.findAllByReleaseDateBefore(releaseDate);
    }

    @Override
    public List<Book> getAllBooksAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepository.findAllByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> getAllBooksWithCopiesLessThan(int copies) {
        return bookRepository.findAllByCopiesIsLessThan(copies);
    }

    @Override
    public List<Book> getAllBooksWithPriceNotBetweenExclusive(BigDecimal lowerThan, BigDecimal higherThan) {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lowerThan, higherThan);
    }

    @Override
    public List<Book> getAllBooksNotReleasedBetweenDates(LocalDate before, LocalDate after) {
        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(before, after);
    }

    @Override
    public List<Book> getBooksByAuthorsLastNameLike(String lastName) {
        return bookRepository.getBooksByAuthorsLastNameLike(lastName);
    }


}
