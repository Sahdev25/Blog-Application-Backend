package com.blog.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Payloads.CommentDto;
import com.blog.Services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable int postId)
	{
		CommentDto saveCommentDto=this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<>(saveCommentDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteComment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<>("Comment Deleted Succussfully",HttpStatus.OK);
	}
}
