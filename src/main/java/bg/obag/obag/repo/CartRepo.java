package bg.obag.obag.repo;

import bg.obag.obag.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {
}
