package com.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.Entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
