package com.renatohvo.rhvoproducts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renatohvo.rhvoproducts.entities.Category;
import com.renatohvo.rhvoproducts.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public List<Category>findAll(){
		return categoryService.findAll();
	}

}
