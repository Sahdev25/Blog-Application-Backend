package com.blog.Services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.CommentToken;

import com.blog.Entities.Comment;
import com.blog.Entities.Post;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Payloads.CommentDto;
import com.blog.Repositories.CommentRepo;
import com.blog.Repositories.PostRepo;
import com.blog.Services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		Comment comment=this.modelMapper.map(commentDto,Comment.class);
	    comment.setPost(post);
	    Comment savedComment=this.commentRepo.save(comment);
	     
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		
		
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepo.delete(comment);
	}

}
