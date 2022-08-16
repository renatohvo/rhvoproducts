package com.renatohvo.rhvoproducts.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.renatohvo.rhvoproducts.entities.Product;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		long id = 1L;
		
		repository.deleteById(id);
		Optional<Product> result = repository.findById(id);
		
		Assertions.assertFalse(result.isPresent());
	}

}
