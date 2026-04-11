package com.tutorials.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorials.models.Reels;
import com.tutorials.models.User;
import com.tutorials.repository.ReelsRepository;

@Service
public class ReelsServiceImplementation implements ReelsService{

	@Autowired
	ReelsRepository reelsRepo;
	
	@Autowired 
	UserService userService;
	
	@Override
	public Reels createReel(Reels reel, User user) {
		Reels createReel = new Reels();
		createReel.setTitle(reel.getTitle());
		createReel.setUser(user);
		createReel.setVideo(reel.getVideo());
		
		return reelsRepo.save(createReel);
	}

	@Override
	public List<Reels> findAllReels() {
		List<Reels> reels = reelsRepo.findAll();
		return reels;
	}

	@Override
	public List<Reels> findUsersReels(Integer userId) throws Exception {
		
		userService.findById(userId);
		
		return reelsRepo.findByUserId(userId);
	}
	
	public String deleteReel(Integer reelId, User user) {

        Reels reel = reelsRepo.findById(reelId)
                .orElseThrow(() -> new RuntimeException("Reel not found"));

        if (!reel.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this reel");
        }

        user.getReels().remove(reel);

        reelsRepo.delete(reel);
        return "Reel deleted successfully";

    }

}
