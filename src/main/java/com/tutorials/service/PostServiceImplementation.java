package com.tutorials.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorials.models.Post;
import com.tutorials.models.User;
import com.tutorials.repository.CommentRepository;
import com.tutorials.repository.PostRepository;
import com.tutorials.repository.UserRepository;

@Service
public class PostServiceImplementation implements PostService {

	@Autowired
	PostRepository postRepo;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CommentRepository commentRepo;
	@Override
	public Post createNewPost(Post post, Integer userId) throws Exception {
		Post newPost = new Post();
		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setCreatedAt(LocalDateTime.now());
		newPost.setVideo(post.getVideo());
		newPost.setUser(userService.findById(userId));
		postRepo.save(newPost);
		return newPost;
	}

	@Override
	public Post deletePost(Integer postId, Integer userId) throws Exception {

	    Post post = findPostById(postId);
	    User user = userService.findById(userId);

	    // ✅ OWNER OR ADMIN
	    if (!post.getUser().getId().equals(user.getId()) 
	        && !user.getRole().equals("ADMIN")) {
	        throw new Exception("You can't delete another user's post");
	    }

	    // 🔥 1. REMOVE LIKES
	    post.getLiked().clear();

	    // 🔥 2. REMOVE FROM SAVED POSTS (IMPORTANT)
	    List<User> users = userRepo.findAll();
	    for (User u : users) {
	        u.getSavedPost().remove(post);
	    }

	    // 🔥 3. DELETE COMMENTS
	    commentRepo.deleteByPost(post);

	    // 🔥 4. DELETE POST
	    postRepo.delete(post);

	    return post;
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) {
		return postRepo.findPostByUserId(userId);
	}

	@Override
	public Post findPostById(Integer postId) throws Exception {
		Optional<Post> opt = postRepo.findById(postId);
		if(opt.isEmpty()) {
			throw new Exception("Post not found with id "+postId);
		}
		return opt.get();
	}

	@Override
	public List<Post> findAllPost() {
		
		return postRepo.findAll();
	}

	@Override
	public User savedPost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findById(userId);
		
		if(user.getSavedPost().contains(post)) {
			user.getSavedPost().remove(post);
		}else {
			user.getSavedPost().add(post);
		}
		userRepo.save(user);
		return user;
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findById(userId);
		
		if(post.getLiked().contains(user)) {
			post.getLiked().remove(user);
		}else {	
			post.getLiked().add(user);
		}
		
		return postRepo.save(post);
	}

}
