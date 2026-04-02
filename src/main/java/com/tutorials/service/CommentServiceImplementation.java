package com.tutorials.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorials.models.Comment;
import com.tutorials.models.Post;
import com.tutorials.models.User;
import com.tutorials.repository.CommentRepository;
import com.tutorials.repository.PostRepository;
import com.tutorials.repository.UserRepository;

@Service
public class CommentServiceImplementation implements CommentService{

	@Autowired
	CommentRepository commentRepo;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PostRepository postRepo;
	
	@Override
	public Comment addComment(Integer userId, Integer postId, String content) throws Exception {
		User user = userRepo.findById(userId)
				.orElseThrow(()->new Exception("User not found"));
		
		Post post = postRepo.findById(postId)
				.orElseThrow(()->new Exception("Post not found"));
		Comment comment = new Comment();
		
		comment.setContent(content);
		comment.setUser(user);
		comment.setPost(post);
		comment.setCreatedAt(LocalDateTime.now());
		post.getComments().add(comment);
		return commentRepo.save(comment);
	}

	@Override
	public List<Comment> getPostComments(Integer postId) {
		List<Comment> comments = commentRepo.findByPostId(postId);
		return comments;
	}

	@Override
	public String deleteComment(Integer commentId) throws Exception {

	    Comment comment = commentRepo.findById(commentId)
	            .orElseThrow(() -> new Exception("Comment not found"));

	    // 🔥 Step 1: Post se relation remove karo
	    Post post = comment.getPost();

	    if (post != null) {
	        post.getComments().remove(comment);
	        postRepo.save(post);   // 👈 IMPORTANT
	    }

	    // 🔥 Step 2: ab delete karo
	    commentRepo.delete(comment);

	    return "Comment deleted successfully";
	}
	
}
