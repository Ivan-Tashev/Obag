package bg.obag.obag.repo;

import bg.obag.obag.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByCategory(String category);

    boolean existsByCategory(String category);

    @Query("SELECT c FROM CategoryEntity c WHERE c.category = :category AND c.id <> :id")
    Optional<CategoryEntity> findByCategoryExceptId(String category, Long id);

    List<CategoryEntity> findAllByOrderByPriorityAsc();
}
