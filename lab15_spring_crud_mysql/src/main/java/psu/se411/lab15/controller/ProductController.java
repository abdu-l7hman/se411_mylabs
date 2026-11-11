package psu.se411.lab15.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import psu.se411.lab15.exception.ObjectNotFoundException;
import psu.se411.lab15.model.Product;
import psu.se411.lab15.service.ProductService;

/** The five CRUD operations on /api/v1/products. */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/** 1. GET /api/v1/products */
	@GetMapping()
	public ResponseEntity<Iterable<Product>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}

	/** 2. GET /api/v1/products/{id} */
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable @Positive Integer id) {
		return productService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	/** 3. POST /api/v1/products */
	@PostMapping()
	public ResponseEntity<Void> create(@Valid @RequestBody Product product, UriComponentsBuilder ucb) {
		Product saved = productService.create(product);
		URI location = ucb.path("/api/v1/products/{id}").buildAndExpand(saved.id()).toUri();
		return ResponseEntity.created(location).build();
	}

	/** 4. PUT /api/v1/products/{id} */
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable @Positive Integer id, @Valid @RequestBody Product product) {
		try {
			productService.update(id, product);
			return ResponseEntity.noContent().build();
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	/** 5. DELETE /api/v1/products/{id} */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable @Positive Integer id) {
		try {
			productService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
