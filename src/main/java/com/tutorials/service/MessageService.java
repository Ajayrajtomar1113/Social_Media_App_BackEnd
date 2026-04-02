package com.tutorials.service;

import java.util.List;

import com.tutorials.models.Chat;
import com.tutorials.models.Message;
import com.tutorials.models.User;

public interface MessageService {

	public Message createMessage(User user,Integer chatId,Message message) throws Exception;

	public List<Message> findChatsMessages(Integer chatId) throws Exception;
	
	
	
}
