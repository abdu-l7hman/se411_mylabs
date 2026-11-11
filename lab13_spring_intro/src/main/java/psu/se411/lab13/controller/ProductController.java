package psu.se411.lab13.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import psu.se411.lab13.model.Product;
import psu.se411.lab13.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductService productService;

	// Spring injects the ProductService bean through this constructor
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/** GET /api/v1/products : all products */
	@GetMapping()
	public ResponseEntity<Iterable<Product>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}

	/**
	 * GET /api/v1/products/{id} : one product.
	 * A negative or zero id is rejected by @Positive -> 400 Bad Request.
	 * An id that does not exist -> 404 Not Found.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(
			@PathVariable @Positive(message = "id must be a positive number") Integer id) {
		return productService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	/** POST /api/v1/products : create a product (body validated with @Valid) */
	@PostMapping()
	public ResponseEntity<Void> create(@Valid @RequestBody Product product, UriComponentsBuilder ucb) {
		Product saved = productService.create(product);
		URI location = ucb.path("/api/v1/products/{id}").buildAndExpand(saved.id()).toUri();
		return ResponseEntity.created(location).build();
	}
}
