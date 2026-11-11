package psu.se411.lab15.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import psu.se411.lab15.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>,
		PagingAndSortingRepository<Product, Integer> {
}
