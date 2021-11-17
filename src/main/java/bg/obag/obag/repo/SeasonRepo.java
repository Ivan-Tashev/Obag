package bg.obag.obag.repo;

import bg.obag.obag.model.entity.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepo extends JpaRepository<SeasonEntity, Long> {

    Optional<SeasonEntity> findBySeason (String season);

    boolean existsBySeason(String season);

    List<SeasonEntity> findAllByOrderByPriority();

    @Query("SELECT s FROM SeasonEntity s WHERE s.season = :season AND s.id <> :id")
    Optional<SeasonEntity> existsBySeasonExceptId(String season, Long id);
}
