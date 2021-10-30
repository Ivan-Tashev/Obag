package bg.obag.obag.repo;


import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.entity.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p ORDER BY p.category, p.sku")
    List<ProductEntity> findAllByCategory();

    List<ProductEntity> findByCategory(Category category);
}
