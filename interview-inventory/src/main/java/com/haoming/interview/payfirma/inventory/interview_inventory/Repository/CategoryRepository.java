package com.haoming.interview.payfirma.inventory.interview_inventory.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.haoming.interview.payfirma.inventory.interview_inventory.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(value="SELECT * FROM category ORDER BY id ASC, parent_id ASC", nativeQuery=true)
	List<Category> findAllCategory();
}
