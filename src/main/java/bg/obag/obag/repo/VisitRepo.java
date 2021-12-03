package bg.obag.obag.repo;

import bg.obag.obag.model.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepo extends JpaRepository<VisitEntity, Long> {
}
