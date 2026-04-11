package com.tutorials.service;

import java.util.List;

import com.tutorials.models.Reels;
import com.tutorials.models.User;

public interface ReelsService {

	public Reels createReel(Reels reel,User user);

	public List<Reels> findAllReels();
	
	public List<Reels> findUsersReels(Integer userId) throws Exception;
	
	public String deleteReel(Integer reelId,User user);
	
}
