package com.softuni.springintroexercises.services.impl;

import com.softuni.springintroexercises.constants.GlobalConstants;
import com.softuni.springintroexercises.entities.Category;
import com.softuni.springintroexercises.repositories.CategoryRepository;
import com.softuni.springintroexercises.services.CategoryService;
import com.softuni.springintroexercises.utils.FileUtil;
import com.softuni.springintroexercises.utils.RandomId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    // utils
    private final FileUtil fileUtil;
    private final RandomId randomID;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        randomID = new RandomId();
    }

    @Override
    public void seedCategories() throws IOException {

        if(categoryRepository.count() != 0){
            return;
        }

        String[] fileContent = fileUtil
                .readFileContent(GlobalConstants.FILE_PATH_CATEGORIES);

        Arrays.stream(fileContent)
                .forEach(r -> categoryRepository.saveAndFlush(new Category(r)));
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> result = new HashSet<>();
        long categoriesCount = this.getCategoriesCount();
        long minCount = 1L;
        long maxCount = categoriesCount / 2L;

        for (long i = minCount; i <= maxCount; i++) {
            result.add(this.getCategoryById(randomID.get(categoriesCount)));
        }
        return result;
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.getOne(id);
    }

    @Override
    public long getCategoriesCount() {
        return this.categoryRepository.count();
    }
}
