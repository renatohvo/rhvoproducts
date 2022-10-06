package com.renatohvo.rhvoproducts.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.renatohvo.rhvoproducts.dto.ProductDTO;
import com.renatohvo.rhvoproducts.entities.Product;
import com.renatohvo.rhvoproducts.repositories.ProductRepository;
import com.renatohvo.rhvoproducts.services.exceptions.DatabaseException;
import com.renatohvo.rhvoproducts.services.exceptions.ResourceNotFoundException;
import com.renatohvo.rhvoproducts.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long idExist;
	private long idNotExist;
	private long idDependent;
	private Product product;
	private PageImpl<Product> page;
	
	@BeforeEach
	void setup() throws Exception {
		idExist = 1L;
		idNotExist = 1000L;
		idDependent = 4L;
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.findById(idExist)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(idNotExist)).thenReturn(Optional.empty());
		
		Mockito.doNothing().when(repository).deleteById(idExist);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(idNotExist);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(idDependent);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository).findAll(pageable);
	}	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(idExist);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExist);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idNotExist);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idNotExist);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdDependent() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(idDependent);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idDependent);
	}

}
