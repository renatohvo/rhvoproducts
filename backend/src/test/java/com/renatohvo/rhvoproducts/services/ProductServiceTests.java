package com.renatohvo.rhvoproducts.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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
import com.renatohvo.rhvoproducts.entities.Category;
import com.renatohvo.rhvoproducts.entities.Product;
import com.renatohvo.rhvoproducts.repositories.CategoryRepository;
import com.renatohvo.rhvoproducts.repositories.ProductRepository;
import com.renatohvo.rhvoproducts.services.exceptions.DatabaseException;
import com.renatohvo.rhvoproducts.services.exceptions.ResourceNotFoundException;
import com.renatohvo.rhvoproducts.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long idExist;
	private long idNotExist;
	private long idDependent;
	private Product product;
	private ProductDTO productDTO;
	private Category category;
	private PageImpl<Product> page;
	
	@BeforeEach
	void setup() throws Exception {
		idExist = 1L;
		idNotExist = 1000L;
		idDependent = 4L;
		product = Factory.createProduct();
		productDTO = Factory.createProductDTO();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(productRepository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(productRepository.findById(idExist)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(idNotExist)).thenReturn(Optional.empty());
		Mockito.when(productRepository.getOne(idExist)).thenReturn(product);
		Mockito.when(productRepository.getOne(idNotExist)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getOne(idExist)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(idNotExist)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(productRepository).deleteById(idExist);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(idNotExist);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(idDependent);
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.update(idExist, productDTO);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(idNotExist, productDTO);
		});
		
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.findById(idExist);
		
		Assertions.assertNotNull(result);
		Mockito.verify(productRepository).findById(idExist);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(idNotExist);
		});
		
		Mockito.verify(productRepository).findById(idNotExist);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(productRepository).findAll(pageable);
	}	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(idExist);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(idExist);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idNotExist);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(idNotExist);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdDependent() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(idDependent);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(idDependent);
	}

}
