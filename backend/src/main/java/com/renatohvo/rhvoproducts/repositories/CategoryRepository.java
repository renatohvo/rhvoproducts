package com.renatohvo.rhvoproducts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renatohvo.rhvoproducts.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
}
