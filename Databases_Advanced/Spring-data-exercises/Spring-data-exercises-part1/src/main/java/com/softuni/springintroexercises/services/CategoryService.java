package com.softuni.springintroexercises.services;

import com.softuni.springintroexercises.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();

    Category getCategoryById(Long id);

    long getCategoriesCount();
}
