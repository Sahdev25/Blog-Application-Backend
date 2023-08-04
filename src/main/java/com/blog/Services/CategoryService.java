package com.blog.Services;

import java.util.List;

import com.blog.Payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,int categoryId);
	void deleteCategory(int categoryId);
	CategoryDto getCategory(int categoryId);
	List<CategoryDto> getAllCategories();
}
