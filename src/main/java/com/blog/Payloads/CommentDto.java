package com.blog.Payloads;

import java.time.LocalDateTime;
import java.util.Set;

import com.blog.Entities.Category;
import com.blog.Entities.Comment;
import com.blog.Entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private int commentId;
	
	private String content;
	
}
