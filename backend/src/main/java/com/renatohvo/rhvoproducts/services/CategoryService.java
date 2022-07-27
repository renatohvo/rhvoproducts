package com.renatohvo.rhvoproducts.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renatohvo.rhvoproducts.dto.CategoryDTO;
import com.renatohvo.rhvoproducts.entities.Category;
import com.renatohvo.rhvoproducts.repositories.CategoryRepository;
import com.renatohvo.rhvoproducts.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();
		return list.stream().map(cat -> new CategoryDTO(cat)).collect(Collectors.toList());		
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category cat = obj.orElseThrow(() -> new EntityNotFoundException("ENTITY NOT FOUND!"));
		return new CategoryDTO(cat);
	}
}
