package com.blog.Payloads;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.blog.Entities.Category;
import com.blog.Entities.Comment;
import com.blog.Entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private int postId;
	private String title;
	private String content;
	private String imageName;
	private LocalDateTime addedDate;
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto> comments=new HashSet<>();
}
