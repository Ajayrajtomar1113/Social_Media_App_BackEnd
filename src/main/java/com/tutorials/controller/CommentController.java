package com.tutorials.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tutorials.models.Comment;
import com.tutorials.models.User;
import com.tutorials.service.CommentService;
import com.tutorials.service.UserService;

@RestController
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;
	@PostMapping("/api/comment/post/{postId}")
	public Comment addComment(@RequestHeader("Authorization") String jwt,@PathVariable Integer postId,@RequestBody String content) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		return commentService.addComment(user.getId(), postId, content);
		
	}
	
	
	@GetMapping("/api/comment/post/{postId}")
	public List<Comment> getPostComments(@PathVariable Integer postId){
		return commentService.getPostComments(postId);
	}
	
	@DeleteMapping("/api/comment/delete/{commentId}")
	public String deleteComment(@PathVariable Integer commentId) throws Exception {
		return commentService.deleteComment(commentId);
	}
}
