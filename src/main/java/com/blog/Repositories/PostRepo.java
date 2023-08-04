package com.blog.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.Entities.Category;
import com.blog.Entities.Post;
import com.blog.Entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByCategory(Category category);
	List<Post> findByUser(User user);
	List<Post> findByTitleContaining(String title);
}
