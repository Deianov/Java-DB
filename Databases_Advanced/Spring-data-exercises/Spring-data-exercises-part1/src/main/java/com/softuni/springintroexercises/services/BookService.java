package com.softuni.springintroexercises.services;

import com.softuni.springintroexercises.entities.Author;
import com.softuni.springintroexercises.entities.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getAllBooksAfter(LocalDate releaseDate);
    List<Book> getAllBooksBefore(LocalDate releaseDate);
    List<Book> findAllBooksByAuthorOrderByReleaseDateDescAndTitleAsc(Author author);
}
