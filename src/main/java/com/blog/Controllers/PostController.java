 package com.blog.Controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.Payloads.PostDto;
import com.blog.Payloads.PostResponse;
import com.blog.Services.FileService;
import com.blog.Services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/post/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> CreatePost(@RequestBody PostDto postDto,@PathVariable("userId") int userId,@PathVariable("categoryId") int categoryId)
	{
		PostDto postDto2=this.postService.CreatePost(postDto, userId, categoryId);
		return new ResponseEntity<>(postDto2,HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") int userId)
	{
		List<PostDto> postDtos=this.postService.getPostsByUser(userId);
		return new ResponseEntity<>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/posts/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") int categoryId)
	{
		List<PostDto> postDtos=this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue = "0",required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue = "10",required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue = "postId",required=false) String sortBy,
			@RequestParam(value="sortDirection",defaultValue="asc",required=false) String sortDirection
			)
	{
		PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDirection);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId)
	{
		PostDto post=this.postService.getPostById(postId);
		return new ResponseEntity<>(post,HttpStatus.OK);
	}
	
	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<?> deletePostById(@PathVariable("postId") int postId)
	{
	    this.postService.deletePost(postId);
		return new ResponseEntity<>("Post deleted Succussfully",HttpStatus.OK);
	}
	
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable("postId") int postId)
	{
		PostDto updatedPost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatedPost,HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords)
	{
		List<PostDto> postsDtos=this.postService.searchPosts(keywords);
		return new ResponseEntity<>(postsDtos,HttpStatus.OK);
	}
	
	@PostMapping("/post/uploadImage/{postId}")
	public ResponseEntity<?> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable int postId) throws IOException
	{
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
	    PostDto updatedPostDto=	this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
	}
	
	@GetMapping("/post/images/{imageName}")
	public void downloadInage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException
	{
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource,response.getOutputStream() ) ;   //copy(resource,response.getOutputStream());
		
	}
}
