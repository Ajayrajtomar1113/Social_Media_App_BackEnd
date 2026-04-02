package com.tutorials.service;

import java.util.List;

import com.tutorials.models.Comment;

public interface CommentService {

	public Comment addComment(Integer userId,Integer postId,String Content) throws Exception;
	
	public List<Comment> getPostComments(Integer postId);
	
	public String deleteComment(Integer commentId) throws Exception; 
}
