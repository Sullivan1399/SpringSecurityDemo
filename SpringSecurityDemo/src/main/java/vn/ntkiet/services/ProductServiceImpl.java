package vn.ntkiet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ntkiet.entity.Product;
import vn.ntkiet.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository repo;
	
	public ProductServiceImpl(ProductRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public Product get(Long id) {
		return repo.getById(id);
	}

	@Override
	public Product save(Product product) {
		return repo.save(product);
	}

	@Override
	public List<Product> listAll() {
		return repo.findAll();
	}
	
}
