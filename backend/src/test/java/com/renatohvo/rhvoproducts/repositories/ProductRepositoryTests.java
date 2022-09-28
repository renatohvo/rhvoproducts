package com.renatohvo.rhvoproducts.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.renatohvo.rhvoproducts.entities.Product;
import com.renatohvo.rhvoproducts.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	private long idExist;
	private long idNotExist;
	private long countTotalProducts;
	
	@Autowired
	private ProductRepository repository;
	
	@BeforeEach
	void setUp() throws Exception {
		idExist = 1L;
		idNotExist = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void findByIdShouldReturnOptionalNotNullWhenIdExists() {
		
		Optional<Product> result = repository.findById(idExist);
		
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnOptionalNullWhenIdNotExists() {
		
		Optional<Product> result = repository.findById(idNotExist);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		repository.deleteById(idExist);
		Optional<Product> result = repository.findById(idExist);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldTrhowEmptyResultDataAccessExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(idNotExist);
		});
	}

}
