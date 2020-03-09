package com.springdata.exercises.services;

import com.springdata.exercises.entities.Author;

import java.util.List;


public interface AuthorService {
    long getAllAuthorsCount();
    Author findAuthorById(Long id);

    List<Author> getAllAuthorsWithFirstNameLike(String firstName);
}
