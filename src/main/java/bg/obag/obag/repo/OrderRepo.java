package bg.obag.obag.repo;

import bg.obag.obag.model.entity.OrderEntity;
import bg.obag.obag.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserOrderByCreatedOnDesc(UserEntity user);
}
