package com.productsshop.repository;

import com.productsshop.model.dtos.query3.CategoryProductsModel;
import com.productsshop.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    // query 3
    @Query("select new com.productsshop.model.dtos.query3.CategoryProductsModel("+
                  " c.name, size(c.products), avg(p.price), sum(p.price)) from Category c "+
                  "join c.products p "+
                  "group by c.id "+
                  "order by size(c.products) desc"
    )
    List<CategoryProductsModel> findAllCategoriesByCountAndPrices();
}
