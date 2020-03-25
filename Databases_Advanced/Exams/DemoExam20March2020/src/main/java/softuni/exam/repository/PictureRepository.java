package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entity.Picture;
import java.util.Optional;

@Repository
public interface PictureRepository  extends JpaRepository<Picture, Long> {
    Optional<Picture> findPictureByUrl(String url);
}
