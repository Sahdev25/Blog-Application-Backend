package com.blog.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Payloads.CategoryDto;
import com.blog.Services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDto> CreateCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto categoryDto2=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(categoryDto2,HttpStatus.CREATED);
	}
	
	@PutMapping("/updateCategory/{catId}")
	public ResponseEntity<CategoryDto> UpdateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("catId") int catId)
	{
		
		CategoryDto categoryDto2=this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<>(categoryDto2,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCategory/{catId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("catId") int catId)
	{
		
	    this.categoryService.deleteCategory(catId);
		return new ResponseEntity<>("Category Deleted Succussfully...",HttpStatus.OK);
	}
	
	@GetMapping("/getByCatId/{catId}")
	public ResponseEntity<CategoryDto> GetCategory(@PathVariable("catId") int catId)
	{
		
		CategoryDto categoryDto2=this.categoryService.getCategory(catId);
		return new ResponseEntity<>(categoryDto2,HttpStatus.OK);
	}
	
	@GetMapping("/getAllCat")
	public ResponseEntity<List<CategoryDto>> GetAllCategories()
	{
		
		List<CategoryDto> categoryDtos=this.categoryService.getAllCategories();
		return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
	}
}
