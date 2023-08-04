package com.blog.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private int categoryId;
	@NotBlank
	@Size(min=3)
	private String categoryTitle;
	@NotBlank
	@Size(max=50)
	private String categoryDescription;
}
