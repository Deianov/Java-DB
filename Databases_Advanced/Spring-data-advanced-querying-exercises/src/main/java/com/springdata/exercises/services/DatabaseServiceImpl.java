package com.springdata.exercises.services;

import com.springdata.exercises.entities.enums.AgeRestriction;
import com.springdata.exercises.entities.enums.EditionType;
import com.springdata.exercises.constants.GlobalConstants;
import com.springdata.exercises.entities.Author;
import com.springdata.exercises.entities.Book;
import com.springdata.exercises.entities.Category;
import com.springdata.exercises.repositories.AuthorRepository;
import com.springdata.exercises.repositories.BookRepository;
import com.springdata.exercises.repositories.CategoryRepository;
import com.springdata.exercises.utils.FileUtil;
import com.springdata.exercises.utils.RandomId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class DatabaseServiceImpl implements DatabaseService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    private final RandomId randomId;
    private final FileUtil fileUtil;

    @Autowired
    public DatabaseServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, CategoryRepository categoryRepository, RandomId randomId, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.randomId = randomId;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seed() throws IOException {
        this.seedAuthors();
        this.seedCategories();
        this.seedBooks();
    }

    @Override
    public void seedAuthors() throws IOException {
        if(authorRepository.count() != 0){
            return;
        }
        fileUtil.readFileContent(GlobalConstants.FILE_PATH_AUTHORS)
                .forEach(r -> {
                    String[] params = r.split("\\s+");
                    String firstName = params[0];
                    String lastName = params[1];

                    if (this.authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName).isEmpty()){
                        authorRepository.saveAndFlush(new Author(firstName, lastName));
                    }
                });
    }

    @Override
    public void seedBooks() throws IOException {
        if(bookRepository.count() != 0){
            return;
        }

        List<String> fileContent = fileUtil
                .readFileContent(GlobalConstants.FILE_PATH_BOOKS);

        for (String row : fileContent) {
            String[] params = row.split("\\s+");

            Author author = authorRepository
                    .getOne(randomId.get(authorRepository.count()));

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

            Set<Category> categories = this.getRandomCategories();

            Book book = new Book(title, null, editionType, price, copies, releaseDate, ageRestriction, categories, author);
            bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public void seedCategories() throws IOException {
        if(categoryRepository.count() != 0){
            return;
        }
        fileUtil.readFileContent(GlobalConstants.FILE_PATH_CATEGORIES)
                .forEach(name -> {
                    if (categoryRepository.getByName(name).isEmpty()){
                        categoryRepository.saveAndFlush(new Category(name));
                    }
                });
    }

    private String getTitle(String[] params) {
        StringBuilder builder = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            builder.append(params[i])
                    .append(" ");
        }
        return builder.toString().trim();
    }

    public Set<Category> getRandomCategories() {
        Set<Category> result = new HashSet<>();
        long categoriesCount = categoryRepository.count();
        long minCount = 1L;
        long maxCount = categoriesCount / 2L;

        for (long i = minCount; i <= maxCount; i++) {
            Category category = categoryRepository.getOne(randomId.get(categoriesCount));
            result.add(category);
        }
        return result;
    }
}
