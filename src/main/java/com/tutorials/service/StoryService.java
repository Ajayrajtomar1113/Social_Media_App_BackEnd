package com.tutorials.service;

import java.util.List;

import com.tutorials.models.Story;
import com.tutorials.models.User;

public interface StoryService {

	public Story createStory(Story story,User user);

	public List<Story> findStoryByUserId(Integer userId) throws Exception;
}
