package com.blog.Services;

import com.blog.Payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,int postId);
	void deleteComment(int commentId);
}
