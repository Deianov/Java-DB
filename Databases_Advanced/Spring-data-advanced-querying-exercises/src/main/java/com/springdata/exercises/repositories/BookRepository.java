package com.springdata.exercises.repositories;

import com.springdata.exercises.constants.AgeRestriction;
import com.springdata.exercises.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
    List<Book> findAllByReleaseDateBefore(LocalDate localDate);
    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);
    List<Book> findAllByCopiesIsLessThan(int copies);
    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerThan, BigDecimal higherThan);

//    @Query("SELECT b FROM Book AS b WHERE FUNCTION('YEAR', b.releaseDate) <> :year")
    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    // Java Persistence Query Language
    @Query("select b from Book b where b.author.lastName like :lastName")
    List<Book> getBooksByAuthorsLastNameLike(@Param(value = "lastName") String lastName);
}