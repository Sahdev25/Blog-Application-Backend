package com.blog.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Entities.Category;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Payloads.CategoryDto;
import com.blog.Payloads.UserDto;
import com.blog.Repositories.CategoryRepo;
import com.blog.Services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
    
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category=this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory=this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		//Category category2=this.modelMapper.map(categoryDto, Category.class);
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category savedCategory=this.categoryRepo.save(category);
		   
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepo.delete(category);
		
	}

	@Override
	public CategoryDto getCategory(int categoryId) {
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories=this.categoryRepo.findAll();
		//List<UserDto> userDtos=users.stream().map(user->this.UserToUserDto(user)).collect(Collectors.toList());
		List<CategoryDto> categoryDtos=categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
