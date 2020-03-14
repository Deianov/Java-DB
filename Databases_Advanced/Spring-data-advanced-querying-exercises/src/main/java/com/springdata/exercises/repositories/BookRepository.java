package com.springdata.exercises.repositories;

import com.springdata.exercises.entities.enums.AgeRestriction;
import com.springdata.exercises.entities.Book;
import com.springdata.exercises.entities.dto.ReducedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Query creation from method names

    List<Book> findAllByReleaseDateBefore(LocalDate localDate);
    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);
    List<Book> findAllByCopiesIsLessThan(int copies);
    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerThan, BigDecimal higherThan);
//    @Query("select b FROM Book AS b WHERE FUNCTION('YEAR', b.releaseDate) <> :year")
    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);
    List<Book> findAllByTitleIsLike(String title);

    // Java Persistence Query Language
    // Example 61. Declare query at the query method using @Query

    // Using Named Parameters
    @Query("select b from Book b where b.author.lastName like :lastName")
    List<Book> getBooksByAuthorsLastNameLike(@Param(value = "lastName") String lastName);

    // Using Parameters by index
    @Query("select count(b) from Book b where length(b.title) > ?1")
    int getCountOfBooksWithTitleLengthLongerThan(int length);

    // Data Transfer Object ?
    Optional<ReducedBook> getBookByTitle(String title);

    @Modifying
    @Transactional
    @Query("update Book b set b.copies = b.copies + :newCopies where b.releaseDate > :date")
    Integer increaseCopiesOfAllBooksReleasedAfter(@Param("date") LocalDate releaseDate ,@Param("newCopies") int copiesToAdd);

    @Modifying
    @Transactional
    Integer deleteAllByCopiesLessThan(int minCopies);

    @Procedure(procedureName = "usp_count_books_by_author_names")
    int uspCountBooksByAuthorNames(@Param("first_name") String firstName, @Param("last_name") String lastName);
}