package com.blog.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.blog.Entities.Role;
import com.blog.Entities.User;
import com.blog.Repositories.UserRepo;

@Component
public class AuthenticationOfUser implements AuthenticationProvider {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email=authentication.getName();
		String password=authentication.getCredentials().toString();
		Optional<User> user=this.userRepo.findByEmail(email);
		if(user.isEmpty())
		{
			throw new BadCredentialsException("User doesn't Exists...");
		}
		if(password.equals(user.get().getPassword()))
		{
			return new UsernamePasswordAuthenticationToken(email,password,getRole(user));
		}
		else {
			throw new BadCredentialsException("Password Mismatch.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	Collection<? extends GrantedAuthority> getRole(Optional<User> user)
	{
		List<Role> roles=(List<Role>) user.get().getRoles();
		List<GrantedAuthority> grantedAuthorities=roles.stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		return grantedAuthorities;
	}
}
