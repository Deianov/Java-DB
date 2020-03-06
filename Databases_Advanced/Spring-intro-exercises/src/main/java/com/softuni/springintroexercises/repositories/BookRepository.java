package com.softuni.springintroexercises.repositories;

import com.softuni.springintroexercises.entities.Author;
import com.softuni.springintroexercises.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
    List<Book> findAllByReleaseDateBefore(LocalDate localDate);
    List<Book> findAllByAuthorOrderByReleaseDateDescTitleAsc(Author author);
}