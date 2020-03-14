package com.springdata.exercises.repositories;

import com.springdata.exercises.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Query creation from method names
    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);
    List<Author> findAllByFirstNameLike(String firstName);


    @Query("select concat(a.firstName, ' ', a.lastName, ' - ', sum(b.copies)) " +
            "from Author a " +
            "join a.books b " +
            "group by a.id " +
            "order by sum(b.copies) desc "
    )
    String[] findNumberOfBookCopiesByAuthor();
}
