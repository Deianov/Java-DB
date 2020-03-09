package com.springdata.exercises.services;

import com.springdata.exercises.entities.Author;
import com.springdata.exercises.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    // inject current repository
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public long getAllAuthorsCount() {
        return authorRepository.count();
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.getOne(id);
    }

    @Override
    public List<Author> getAllAuthorsWithFirstNameLike(String firstName) {
        return authorRepository.findAllByFirstNameLike(firstName);
    }

}
