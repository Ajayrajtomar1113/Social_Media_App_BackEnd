package com.tutorials.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorials.config.JwtProvider;
import com.tutorials.exceptions.UserException;
import com.tutorials.models.Post;
import com.tutorials.models.User;
import com.tutorials.repository.ChatRepository;
import com.tutorials.repository.CommentRepository;
import com.tutorials.repository.PostRepository;
import com.tutorials.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PostRepository postRepo;
	
	@Autowired
	ChatRepository chatRepo;
	
	@Override
	public User registerUser(User user) {
	    User savedUser = repo.save(user);
	    return savedUser;
	}


	@Override
	public User findById(Integer userId) throws UserException {
		Optional<User> user = repo.findById(userId);
		
		if(user.isPresent()) {
			return user.get();
		}
		
		throw new UserException("User not found");
	}

	@Override
	public User findUserByEmail(String email){
		User user = repo.findByEmail(email);
		return user;
	}

	@Override
	public User followUser(Integer reqUserId, Integer userId2) throws UserException {
		if(reqUserId.equals(userId2)) {
	        throw new UserException("You cannot follow yourself");
	    }
		User reqUser = findById(reqUserId);
		User user2 = findById(userId2);
		
		if(user2.getFollowers().contains(reqUser.getId())) {
		    // unfollow
		    user2.getFollowers().remove(reqUser.getId());
		    reqUser.getFollowings().remove(user2.getId());
		} else {
		    // follow
		    user2.getFollowers().add(reqUser.getId());
		    reqUser.getFollowings().add(user2.getId());
		}
		
		repo.save(reqUser);
		repo.save(user2);
		return reqUser;
	}
	


	@Override
	public User updateUser(User user,Integer userId) {
		User oldUser = repo.findById(userId)
	            .orElseThrow(() -> new RuntimeException(
	                    "User not exist with id = " + userId));
		
		
		
		if(user.getFirstName() != null) {
			oldUser.setFirstName(user.getFirstName());
		}
		if(user.getLastName() != null) {
			oldUser.setLastName(user.getLastName());
		}
		if(user.getEmail() != null) {
			oldUser.setEmail(user.getEmail());
		}
		if(user.getPassword() != null) {
			oldUser.setPassword(user.getPassword());
		}
		
		 return repo.save(oldUser);
	}

	@Override
	public List<User> searchUser(String query) {
		return repo.searchUser(query);
	}

	@Override
	public List<User> findAllUser() {
	    return repo.findAll();
	}


	@Override
	public User findUserByJwt(String jwt) {
		if(jwt != null && jwt.startsWith("Bearer ")) {
	        jwt = jwt.substring(7);
	    }
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		User user = repo.findByEmail(email);
		return user;
	}


	@Transactional
	public User deleteUser(Integer userId) {

	    User user = repo.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // 🔥 1. Remove user from chats (VERY IMPORTANT - FK fix)
	    chatRepo.removeUserFromChats(userId);

	    // 🔥 2. Delete comments of this user
	    commentRepository.deleteByUser(user);

	    // 🔥 3. Remove user from liked posts (ManyToMany cleanup)
	    List<Post> posts = postRepo.findAll();
	    for (Post post : posts) {
	        post.getLiked().remove(user);
	    }

	    // 🔥 4. Remove user from all saved posts (correct + clean)
	    List<User> users = repo.findAll();
	    for (User u : users) {
	        u.getSavedPost().removeIf(p -> 
	            p != null && 
	            p.getUser() != null && 
	            p.getUser().getId().equals(userId)
	        );
	    }

	    // 🔥 5. Clear user's own saved posts
	    user.getSavedPost().clear();

	    // 🔥 6. Delete user (cascade handles posts, reels, comments)
	    repo.delete(user);

	    return user;
	}

	

}
