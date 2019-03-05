package com.haoming.interview.payfirma.inventory.interview_inventory.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haoming.interview.payfirma.inventory.interview_inventory.Repository.CategoryRepository;
import com.haoming.interview.payfirma.inventory.interview_inventory.Repository.ProductRepository;
import com.haoming.interview.payfirma.inventory.interview_inventory.model.Category;
import com.haoming.interview.payfirma.inventory.interview_inventory.model.Product;

@Controller
@CrossOrigin(origins="*")
@RequestMapping(path="/product")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(value="/get_all_products", produces = "application/json")
	@ResponseBody
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@RequestMapping(value="/get_product_by_category", produces = "application/json")
	@ResponseBody
	public List<Product> getProductByCategory(@RequestParam(value="category_id") long categoryId){
		if(categoryId == 0) {
			return productRepository.findAll();
		} else {
			return productRepository.findProductByCategoryId(categoryId);
		}
	}
	
	@RequestMapping(value="/get_all_categories", produces = "application/json")
	@ResponseBody
	public List<Category> getAllCategories(){
		List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "parentId"));
		return findNextSubCategory(0, categories, 1);
	}
	
	@RequestMapping(value="/delete_product", produces = "application/json", method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Long> deleteProduct(@RequestParam(value="product_id") long productId) {
		productRepository.deleteById(productId);
		return Collections.singletonMap("product_id", productId);
	}
	
	private List<Category> findNextSubCategory(int currentId, List<Category> categories, int depth){
		List<Category> result = new ArrayList<Category>();
		boolean isFound = false;
		if(categories.get(currentId) != null) {
			for(int i=currentId; i < categories.size(); i++) {
				if(depth == 1) {
					if(categories.get(i).getParentId() == 0) {
						isFound = true;
					}
				} else if (depth > 1) {
					if(categories.get(currentId).getId() == categories.get(i).getParentId()) {
						isFound = true;
					}
				}
				
				if(isFound) {
					result.add(categories.get(i));
					result.addAll(findNextSubCategory(i, categories, depth+1));
					isFound = false;
				}
				
			}
		}
		return result;
	}
}
