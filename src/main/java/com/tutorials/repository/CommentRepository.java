package com.tutorials.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorials.models.Comment;
import com.tutorials.models.Post;
import com.tutorials.models.User;

public interface CommentRepository extends JpaRepository<Comment,Integer>{
	
	List<Comment> findByPostId(Integer postId);
	void deleteByUser(User user);
	
	void deleteByPost(Post post);
	
	

}
