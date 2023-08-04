package com.blog.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.Payloads.UserDto;
import com.blog.Services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	@PostMapping("/addUser")
	ResponseEntity<UserDto> AddUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto savedUserDto=this.userService.createUser(userDto);
	   return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/updateUser/{id}")
	ResponseEntity<UserDto> UpdateUser(@Valid @RequestBody UserDto userDto,@PathVariable("id") int id)
	{
		UserDto updatedUserDto=this.userService.updateUser(userDto, id);
		return new ResponseEntity<>(updatedUserDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	ResponseEntity<?> DeleteUser(@PathVariable("id") int id)
	{
		this.userService.deleteUser(id);
		return new ResponseEntity<>("User Deleted Succussfully...",HttpStatus.OK);
	}
	@GetMapping("/getAllUsers")
	ResponseEntity<List<UserDto>> GetAllUsers()
	{
		List<UserDto> userDtos=this.userService.getAllUsers();
		return new ResponseEntity<>(userDtos,HttpStatus.OK);
	}
	
	@GetMapping("/getById/{id}")
	ResponseEntity<UserDto> GetById(@PathVariable("id") int id)
	{
      UserDto userDto=this.userService.getUserById(id);
      return new ResponseEntity<>(userDto,HttpStatus.OK);					
	}
}
