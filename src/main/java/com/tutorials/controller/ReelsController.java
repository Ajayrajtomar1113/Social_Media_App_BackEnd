package com.tutorials.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tutorials.models.Reels;
import com.tutorials.models.User;
import com.tutorials.response.ApiResponse;
import com.tutorials.service.ReelsService;
import com.tutorials.service.UserService;

@RestController
public class ReelsController {

	@Autowired
	private ReelsService reelsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	@PostMapping("/api/reels")
	public Reels createReels(@RequestBody Reels reel,@RequestHeader("Authorization") String jwt) {
		User reqUser = userService.findUserByJwt(jwt);
		
		Reels createdReel = reelsService.createReel(reel, reqUser);
		return createdReel;
	}
	
	@GetMapping("/api/reels")
	public List<Reels> findAllReels(){
		
		List<Reels> reels = reelsService.findAllReels();
		
		return reels;
	}
	
	@PostMapping("/api/reels/users/{userId}")
	public List<Reels> findUsersReels(@PathVariable Integer userId) throws Exception {
	
		List<Reels> reels = reelsService.findUsersReels(userId);
		return reels;
	}
	
	@DeleteMapping("/api/reel/{reelId}")
	public ResponseEntity<ApiResponse> deleteReel(@PathVariable Integer reelId,@RequestHeader("Authorization") String token) {

	        User user = userService.findUserByJwt(token);

	        String msg = reelsService.deleteReel(reelId, user);
	        messagingTemplate.convertAndSend("/topic/delete-reel", reelId);
	        return ResponseEntity.ok(new ApiResponse(msg, true));
	    }
	
}
