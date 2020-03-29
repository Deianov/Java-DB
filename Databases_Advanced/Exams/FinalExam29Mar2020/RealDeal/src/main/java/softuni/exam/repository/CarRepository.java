package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByMakeAndModelAndKilometers(String make, String model, Integer kilometers);
    Optional<Car> findById(Long id);

    @Query("select c from Car c left join Picture p on c.id = p.car.id group by c.id order by count(p.id) desc, c.make")
    List<Car> findAllSorted();
}
