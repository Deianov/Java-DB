package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("select c from Category c join c.items i "+
            "group by c.id "+
            "order by c.items.size desc, sum(i.price) desc "
    )
    List<Category> getCategoriesOrderByItems();
}
