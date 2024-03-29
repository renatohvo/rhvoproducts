package com.renatohvo.rhvoproducts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renatohvo.rhvoproducts.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
