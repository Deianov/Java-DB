package com.springdata.exercises.services;

import com.springdata.exercises.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorServiceImpl implements AuthorService {

    // inject current repository
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public String[] resultTask6(String input){
        return
        authorRepository
                .findAllByFirstNameLike("%" + input)
                .stream()
                .map(author -> String.format("%s %s",
                        author.getFirstName(),
                        author.getLastName())
                )
                .toArray(String[]::new);
    }

    public String[] resultTask10(){
        return authorRepository.findNumberOfBookCopiesByAuthor();
    }
}
