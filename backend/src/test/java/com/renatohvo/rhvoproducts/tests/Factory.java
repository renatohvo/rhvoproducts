package com.renatohvo.rhvoproducts.tests;

import java.time.Instant;

import com.renatohvo.rhvoproducts.dto.ProductDTO;
import com.renatohvo.rhvoproducts.entities.Category;
import com.renatohvo.rhvoproducts.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1L, "iPhone14", "New iPhone 2022", 9000.0, "https://image.org/img.png", Instant.parse("2022-09-26T22:00:00Z"));
		product.getCategories().add(new Category(2L,"Eletronics"));
		return product;
	}
	
	public static ProductDTO createProductDto() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
}
