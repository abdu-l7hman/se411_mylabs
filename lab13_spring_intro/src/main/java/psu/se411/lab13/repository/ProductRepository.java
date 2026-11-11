package psu.se411.lab13.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import psu.se411.lab13.model.Product;

/**
 * Spring creates the implementation automatically and puts it in the IoC container.
 * CrudRepository gives the CRUD operations, PagingAndSortingRepository adds paging and sorting.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>,
		PagingAndSortingRepository<Product, Integer> {
}
