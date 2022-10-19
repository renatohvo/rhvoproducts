package com.renatohvo.rhvoproducts.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.renatohvo.rhvoproducts.repositories.ProductRepository;
import com.renatohvo.rhvoproducts.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository productRepository;
	
	private Long idExist;
	private Long idNotExist;
	private Long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		idExist = 1L;
		idNotExist = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		
		service.delete(idExist);
		
		Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idNotExist);
		});
	}
	
}
