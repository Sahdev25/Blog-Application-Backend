package com.blog.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.Entities.User;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Payloads.UserDto;
import com.blog.Repositories.UserRepo;
import com.blog.Services.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user=this.UserDtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		
		
		
		return this.UserToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new com.blog.Exceptions.ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User savedUser=this.userRepo.save(user);
		UserDto userDto1=this.UserToUserDto(savedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new com.blog.Exceptions.ResourceNotFoundException("User","Id",userId));
		return this.UserToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

        List<User> users=this.userRepo.findAll();
        List<UserDto> userDtos=users.stream().map(user->this.UserToUserDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new com.blog.Exceptions.ResourceNotFoundException("User","Id",userId));
	    this.userRepo.delete(user);
	}
	
	
	private UserDto UserToUserDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}
	
	private User UserDtoToUser(UserDto userDto)
	{
		User user=this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}

}
