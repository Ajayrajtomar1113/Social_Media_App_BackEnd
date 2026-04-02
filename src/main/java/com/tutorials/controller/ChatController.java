package com.tutorials.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import com.tutorials.models.Chat;
import com.tutorials.models.User;
import com.tutorials.request.CreateChatRequest;
import com.tutorials.service.ChatService;
import com.tutorials.service.UserService;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/chats")
	public Chat createChat(@RequestBody CreateChatRequest req,@RequestHeader("Authorization") String jwt) throws Exception
	{
		User reqUser = userService.findUserByJwt(jwt);
		User user2 = userService.findById(req.getUserId());
		Chat chat = chatService.createChat(reqUser, user2);
		return chat;
	}
	
	@GetMapping("/api/chats")
	public List<Chat> findUserChats(@RequestHeader("Authorization") String jwt)
	{
		User user = userService.findUserByJwt(jwt);
		return chatService.findUserChats(user.getId());
	}
	
	
}
