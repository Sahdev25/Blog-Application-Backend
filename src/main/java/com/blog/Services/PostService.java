package com.blog.Services;

import java.util.List;

import com.blog.Entities.Post;
import com.blog.Payloads.PostDto;
import com.blog.Payloads.PostResponse;

public interface PostService {

	PostDto CreatePost(PostDto postDto, int postId, int categoryId);
	
	PostDto updatePost(PostDto postDto,int postId);
	
	void deletePost(int postId);
	
	PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDirection);
	
	PostDto getPostById(int postId);
	
	List<PostDto> getPostsByCategory(int categoryId);
	
	List<PostDto> getPostsByUser(int userId);
	
	List<PostDto> searchPosts(String keword);
}
