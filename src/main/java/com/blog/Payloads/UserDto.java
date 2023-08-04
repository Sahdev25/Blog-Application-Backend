package com.blog.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min = 3,message = "Username must be atleast of three Characters!! ")
	private String name;
	@Email(message = "Email Address is not valid!!")
	private String email;
	@NotEmpty
	@Size(min=3,max = 10,message="Password must be more than or equal to three characters or less than equal to ten characters!!")
	private String password;
	@NotEmpty
	private String about;
}
