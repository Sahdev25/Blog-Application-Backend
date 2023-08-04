package com.blog.Services.Impl;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.loading.PrivateClassLoader;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.Entities.Category;
import com.blog.Entities.Post;
import com.blog.Entities.User;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Payloads.PostDto;
import com.blog.Payloads.PostResponse;
import com.blog.Repositories.CategoryRepo;
import com.blog.Repositories.PostRepo;
import com.blog.Repositories.UserRepo;
import com.blog.Services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto CreatePost(PostDto postDto,int userId, int categoryId) {

		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
        Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(LocalDateTime.now());
		post.setUser(user);
		post.setCategory(category);
		Post savedPost =this.postRepo.save(post);
		
        return this.modelMapper.map(savedPost,PostDto.class);
	}
	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		if(postDto.getAddedDate()!=null)
		{
		post.setAddedDate(postDto.getAddedDate());
		
		}
		if(postDto.getImageName()!=null) 
		{
		post.setImageName(postDto.getImageName());
		}
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
		
	}

	@Override
	public void deletePost(int postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDirection) {
		Sort sort=null;
		if(sortDirection.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		PageRequest p=PageRequest.of(pageNumber, pageSize, sort);
		//Pageable p=(Pageable) PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=this.postRepo.findAll(p);  
		List<Post> posts=pagePost.getContent();
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(int categoryId) {
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts= this.postRepo.findByCategory(category);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(int userId) {
		
		User user =this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts=this.postRepo.findByUser(user);
        List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	
}
