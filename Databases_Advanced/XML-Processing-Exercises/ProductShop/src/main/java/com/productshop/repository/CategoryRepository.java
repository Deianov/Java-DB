package com.productshop.repository;

import com.productshop.model.dto.view.query3.CategoryByProductsCountDto;
import com.productshop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    // query 3
    @Query("select new com.productshop.model.dto.view.query3.CategoryByProductsCountDto("+
            " c.name, c.products.size , avg(p.price), sum(p.price)) from Category c "+
            "join c.products p "+
            "group by c.id "+
            "order by c.products.size desc"
    )
    List<CategoryByProductsCountDto> findAllCategoriesByCountAndPrices();
}
