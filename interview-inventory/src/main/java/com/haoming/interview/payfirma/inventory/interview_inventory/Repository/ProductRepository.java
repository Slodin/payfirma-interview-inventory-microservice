package com.haoming.interview.payfirma.inventory.interview_inventory.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.haoming.interview.payfirma.inventory.interview_inventory.model.Product;

public interface ProductRepository extends JpaRepository <Product, Long> {

	@Query(value = "SELECT * FROM product p, product_category pc WHERE pc.category_id = :category_id and p.id = pc.product_id",
			nativeQuery = true)
	List<Product> findProductByCategoryId(
			@Param("category_id") long categoryId);
	
}
