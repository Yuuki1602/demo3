package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.product.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
    public  Long countById(Long id);
}
