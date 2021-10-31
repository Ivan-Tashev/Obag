package bg.obag.obag.repo;

import bg.obag.obag.model.entity.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepo extends JpaRepository<SeasonEntity, Long> {

    Optional<SeasonEntity> findBySeason (String season);
}
