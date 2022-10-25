package com.renatohvo.rhvoproducts.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.renatohvo.rhvoproducts.dto.ProductDTO;
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
	public void findAllPagedShouldReturnSortedPageWhenSortedByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Cell Phone", result.getContent().get(0).getName());
		Assertions.assertEquals("Lego", result.getContent().get(1).getName());
		Assertions.assertEquals("Notebook A", result.getContent().get(2).getName());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyWhenPageDoesNotExist() {
		
		PageRequest pageRequest = PageRequest.of(20, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldReturnPageSize10() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
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
