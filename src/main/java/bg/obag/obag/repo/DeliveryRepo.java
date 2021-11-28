package bg.obag.obag.repo;

import bg.obag.obag.model.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepo extends JpaRepository<DeliveryEntity, Long> {
}
