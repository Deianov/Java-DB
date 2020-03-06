package com.softuni.springintroexercises.services.impl;

import com.softuni.springintroexercises.constants.GlobalConstants;
import com.softuni.springintroexercises.entities.Author;
import com.softuni.springintroexercises.repositories.AuthorRepository;
import com.softuni.springintroexercises.services.AuthorService;
import com.softuni.springintroexercises.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {

        if(authorRepository.count() != 0){
            return;
        }

        String[] fileContent = fileUtil
                .readFileContent(GlobalConstants.FILE_PATH_AUTHORS);

        Arrays.stream(fileContent)
                .forEach(r -> {
                    String[] params = r.split("\\s+");
                    Author author = new Author(params[0], params[1]);
                    authorRepository.saveAndFlush(author);
                });
    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.getOne(id);
    }

    @Override
    public Author findAuthorByNames(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Author> findAllAuthorsByCountOfBooks() {
        return authorRepository.findAllAuthorByCountOfBook();
    }
}
