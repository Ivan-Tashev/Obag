package bg.obag.obag.repo;

import bg.obag.obag.model.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends JpaRepository<LogEntity, Long> {
}
