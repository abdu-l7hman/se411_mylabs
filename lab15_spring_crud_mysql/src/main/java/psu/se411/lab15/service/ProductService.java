package psu.se411.lab15.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import psu.se411.lab15.exception.ObjectNotFoundException;
import psu.se411.lab15.model.Product;
import psu.se411.lab15.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	public Product create(Product product) {
		// id is AUTO_INCREMENT in MySQL: never trust an id sent by the client
		return productRepository.save(new Product(null, product.name(), product.description(), product.price()));
	}

	public void update(Integer id, Product product) {
		if (!productRepository.existsById(id)) {
			throw new ObjectNotFoundException("Product " + id + " not found");
		}
		productRepository.save(new Product(id, product.name(), product.description(), product.price()));
	}

	public void delete(Integer id) {
		if (!productRepository.existsById(id)) {
			throw new ObjectNotFoundException("Product " + id + " not found");
		}
		productRepository.deleteById(id);
	}
}
