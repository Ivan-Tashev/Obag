package bg.obag.obag.repo;


import bg.obag.obag.model.entity.CategoryEntity;
import bg.obag.obag.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.deleted = false ORDER BY p.category.category, p.sku")
    List<ProductEntity> findAllByCategory();

    List<ProductEntity> findByCategoryAndDeletedIsFalse(CategoryEntity category);

    boolean existsByName(String name);

    boolean existsBySku(String sku);

    boolean existsByBarcode(Long barcode);

    @Query("SELECT p FROM ProductEntity p WHERE p.name = :name AND p.id <> :id")
    Optional<ProductEntity> findByNameExceptId(String name, Long id);

    @Query("SELECT p FROM ProductEntity p WHERE p.sku = :sku AND p.id <> :id")
    Optional<ProductEntity> findBySkuExceptId(String sku, Long id);

    @Query("SELECT p FROM ProductEntity p WHERE p.barcode = :barcode AND p.id <> :id")
    Optional<ProductEntity> findByBarcodeExceptId(Long barcode, Long id);
}
