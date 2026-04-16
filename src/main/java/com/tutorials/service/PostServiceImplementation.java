package com.tutorials.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorials.models.Post;
import com.tutorials.models.Role;
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

	@Transactional
	@Override
	public String deletePost(Integer postId, Integer userId) throws Exception {

	    Post post = findPostById(postId);
	    User user = userService.findById(userId);

	    if (!post.getUser().getId().equals(user.getId())
	            && user.getRole() != Role.ADMIN) {
	        throw new Exception("You can't delete another user's post");
	    }

	    postRepo.deleteLikesByPostId(postId);

	    List<User> users = userRepo.findAll();
	    for (User u : users) {
	        u.getSavedPost().remove(post);
	    }
	    userRepo.saveAll(users);

	    postRepo.delete(post);

	    return "Post deleted successfully";
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

//	@Override
//	public User savedPost(Integer postId, Integer userId) throws Exception {
//		Post post = findPostById(postId);
//		User user = userService.findById(userId);
//		
//		if(user.getSavedPost().contains(post)) {
//			user.getSavedPost().remove(post);
//		}else {
//			user.getSavedPost().add(post);
//		}
//		userRepo.save(user);
//		return user;
//	}
	@Override
	public User savedPost(Integer postId, Integer userId) throws Exception {

	    Post post = findPostById(postId);
	    User user = userService.findById(userId);

	    boolean isSaved = user.getSavedPost()
	            .stream()
	            .anyMatch(p -> p.getId().equals(postId));

	    if (isSaved) {
	        // UNSAVE
	        user.getSavedPost()
	                .removeIf(p -> p.getId().equals(postId));
	    } else {
	        // SAVE
	        user.getSavedPost().add(post);
	    }

	    return userRepo.save(user);
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
