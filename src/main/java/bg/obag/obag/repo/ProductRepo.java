package bg.obag.obag.repo;


import bg.obag.obag.model.entity.Product;
import bg.obag.obag.model.entity.enums.Category;
import bg.obag.obag.model.service.ProductServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p ORDER BY p.category, p.sku")
    List<Product> findAllByCategory();

    List<Product> findByCategory(Category category);
}
