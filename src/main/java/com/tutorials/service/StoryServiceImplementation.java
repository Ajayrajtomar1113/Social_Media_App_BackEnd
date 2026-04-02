package com.tutorials.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorials.models.Story;
import com.tutorials.models.User;
import com.tutorials.repository.StoryRepository;

@Service
public class StoryServiceImplementation implements StoryService{

	@Autowired
	private StoryRepository storyRepo;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<Story> findStoryByUserId(Integer userId) throws Exception {
		User user = userService.findById(userId);
		
		return storyRepo.findByUserId(userId);
	}

	@Override
	public Story createStory(Story story, User user) {
		
		Story createdStory = new Story();
		createdStory.setCaption(story.getCaption());
		createdStory.setImage(story.getImage());
		createdStory.setUser(user);
		createdStory.setTimestamp(LocalDateTime.now());
		
		return storyRepo.save(createdStory);
	}

	
}
