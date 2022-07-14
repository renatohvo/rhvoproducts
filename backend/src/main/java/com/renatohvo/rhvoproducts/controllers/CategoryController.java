package com.renatohvo.rhvoproducts.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renatohvo.rhvoproducts.entities.Category;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@GetMapping
	public ResponseEntity<List<Category>>findAll(){
		List<Category> listCategory = new ArrayList<>();
		listCategory.add(new Category(1L, "Eletronics"));
		listCategory.add(new Category(2L, "Bags"));
		return ResponseEntity.ok().body(listCategory);
	}
	
}
