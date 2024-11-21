package vn.ntkiet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.ntkiet.entity.Product;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("SELECT u FROM products p WHERE p.name = :name")
	public Product getProductByName(@Param("name") String name);
	Optional<Product> findByName(String name);
	Optional<Product> findByBrand(String brand);
}
