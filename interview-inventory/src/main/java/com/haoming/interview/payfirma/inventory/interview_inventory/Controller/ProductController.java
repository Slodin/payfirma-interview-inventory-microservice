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
//		List<Category> categories = categoryRepository.findAllCategory();
		
		
		return categories;
	}
	
	@RequestMapping(value="/delete_product", produces = "application/json", method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Long> deleteProduct(@RequestParam(value="product_id") long productId) {
		productRepository.deleteById(productId);
		return Collections.singletonMap("product_id", productId);
	}
	
	
	//Save
	@RequestMapping(value="/save_category", method=RequestMethod.POST)
	public String saveCategory(@RequestBody Category category) {
		categoryRepository.save(category);
		return "Category Saved!";
	}
	
	private List<Category> subCategory(List<Category> categories){
		List<Category> result = new ArrayList<Category>();
		for(int i=0; i < categories.size(); i++) {
			if(categories.get(i).getParentId() == 0) {
				result.add(categories.get(i));
				categories.remove(i);
				for(int j=i; j < categories.size(); j++) {
					
				}
			}
		}
		
		
		return result;
	}
	
	private Category findNextSubCategory(int currentParentId, List<Category> categories, int matchId){
		List<Category> result = new ArrayList<Category>();
		if(categories.get(currentParentId) != null) {
			for(int i=currentParentId; i < categories.size(); i++) {
				if(categories.get(i).getParentId() == 0) {
					result.add(categories.get(i));
				}
				if(categories.get(i).getParentId() != 0 ) {
					
				}
			}
		}
		
		
		
		return null;
	}
}
