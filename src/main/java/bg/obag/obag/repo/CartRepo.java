package bg.obag.obag.repo;

import bg.obag.obag.model.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, Long> {

    @Query("SELECT c FROM CartEntity c WHERE c.createdOn < :now")
    List<CartEntity> findAllOlderThanSixMonths(LocalDateTime now);

    @Query("DELETE FROM CartEntity c WHERE c.createdOn < :now")
    void deleteOlderThanSixMonths(LocalDateTime now);


}
