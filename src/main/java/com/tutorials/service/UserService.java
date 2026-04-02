package com.tutorials.service;

import java.util.List;

import com.tutorials.exceptions.UserException;
import com.tutorials.models.User;

public interface UserService {
	
	public User registerUser(User user);
	
	public User findById(Integer userId) throws UserException;
	
	public User findUserByEmail(String email);
	
	public List<User> findAllUser();
	
	public User followUser(Integer userId1,Integer userId2) throws UserException;
	
	public User updateUser(User user,Integer userId);
	
	public List<User> searchUser(String query);

	public User findUserByJwt(String jwt);
	
//	public User unFollowUser(Integer userId,Integer userId2) throws UserException;
	
}
