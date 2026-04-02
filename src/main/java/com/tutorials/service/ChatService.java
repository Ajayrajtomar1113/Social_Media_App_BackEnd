package com.tutorials.service;

import java.util.List;

import com.tutorials.models.Chat;
import com.tutorials.models.User;

public interface ChatService {

	public Chat createChat(User reqUser,User user);
	public Chat findChatById(Integer chatId) throws Exception;
	public List<Chat> findUserChats(Integer userId);

}
