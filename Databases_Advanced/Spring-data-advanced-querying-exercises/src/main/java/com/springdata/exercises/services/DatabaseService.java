package com.springdata.exercises.services;

import java.io.IOException;

public interface DatabaseService {
    void seed() throws IOException;
    void seedAuthors() throws IOException;
    void seedBooks() throws IOException;
    void seedCategories() throws IOException;
}
