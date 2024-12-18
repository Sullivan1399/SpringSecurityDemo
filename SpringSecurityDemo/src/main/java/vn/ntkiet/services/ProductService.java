package vn.ntkiet.services;

import java.util.List;

import vn.ntkiet.entity.Product;

public interface ProductService {
	void delete(Long id);
	Product get(Long id);
	Product save(Product product);
	List<Product> listAll();
}
