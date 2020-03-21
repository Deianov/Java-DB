package com.productsshop.repository;

import com.productsshop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("select u from User u "+
//            "join Product p on u.id = p.seller.id "+
//            "where p.buyer is not null "+
//            "group by u.id"
//    )
    @Query(
            "select u from User u where u.sold.size > 0 "+
            "order by u.lastName, u.firstName"
    )
    List<User> findAllUsersWithSoldProducts();
}
