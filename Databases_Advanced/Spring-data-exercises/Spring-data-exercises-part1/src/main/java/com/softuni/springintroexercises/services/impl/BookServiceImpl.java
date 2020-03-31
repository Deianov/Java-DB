package com.softuni.springintroexercises.services.impl;

import com.softuni.springintroexercises.constants.AgeRestriction;
import com.softuni.springintroexercises.constants.EditionType;
import com.softuni.springintroexercises.constants.GlobalConstants;
import com.softuni.springintroexercises.entities.Author;
import com.softuni.springintroexercises.entities.Book;
import com.softuni.springintroexercises.entities.Category;
import com.softuni.springintroexercises.repositories.BookRepository;
import com.softuni.springintroexercises.services.AuthorService;
import com.softuni.springintroexercises.services.BookService;
import com.softuni.springintroexercises.services.CategoryService;
import com.softuni.springintroexercises.utils.FileUtil;
import com.softuni.springintroexercises.utils.RandomId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    // inject current repository
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;
    // inject another services
    private final AuthorService authorService;
    private final CategoryService categoryService;
    // utils
    private final RandomId randomId;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.randomId = new RandomId();
    }

    @Override
    public void seedBooks() throws IOException {

        if(bookRepository.count() != 0){
            return;
        }

        String[] fileContent = fileUtil
                .readFileContent(GlobalConstants.FILE_PATH_BOOKS);

        for (String row : fileContent) {
            String[] params = row.split("\\s+");

            Author author = authorService
                    .findAuthorById(randomId.get(authorService.getAllAuthorsCount()));

            // enum
            EditionType editionType = EditionType
                    .values()[Integer.parseInt(params[0])];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(params[1], formatter);

            int copies = Integer.parseInt(params[2]);

            BigDecimal price = new BigDecimal(params[3]);

            // enum
            AgeRestriction ageRestriction = AgeRestriction
                    .values()[Integer.parseInt(params[4])];

            String title = this.getTitle(params);

            Set<Category> categories = categoryService.getRandomCategories();

            Book book = new Book(title, null, editionType, price, copies, releaseDate, ageRestriction, categories, author);
            bookRepository.saveAndFlush(book);
        }
    }



    private String getTitle(String[] params) {
        StringBuilder builder = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            builder.append(params[i])
                    .append(" ");
        }
        return builder.toString().trim();
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
    public List<Book> findAllBooksByAuthorOrderByReleaseDateDescAndTitleAsc(Author author) {
        return bookRepository.findAllByAuthorOrderByReleaseDateDescTitleAsc(author);
    }
}
