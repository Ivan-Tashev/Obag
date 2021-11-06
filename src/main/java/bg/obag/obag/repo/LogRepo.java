package bg.obag.obag.repo;

import bg.obag.obag.model.custom.ProductsLogCount;
import bg.obag.obag.model.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepo extends JpaRepository<LogEntity, Long> {

    List<LogEntity> findAllByOrderByUser();

    @Query("SELECT l.product.id AS id, l.product.name AS name, l.product.sku AS sku, " +
            "l.product.category.category AS category, l.product.season.season AS season, " +
            "l.product.price AS price, COUNT(l.product.name) as totalName " +
            "FROM LogEntity l GROUP BY l.product.name ORDER BY count(l.product.name) DESC")
    List<ProductsLogCount> findAllByGroupByProduct();
}
