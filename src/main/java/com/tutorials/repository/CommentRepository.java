package com.tutorials.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorials.models.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer>{
	
	List<Comment> findByPostId(Integer postId);

}
