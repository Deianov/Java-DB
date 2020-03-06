package com.softuni.springintroexercises.services;

import com.softuni.springintroexercises.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    int getAllAuthorsCount();

    Author findAuthorById(Long id);
    Author findAuthorByNames(String firstName, String lastName);

    List<Author> findAllAuthorsByCountOfBooks();
}
