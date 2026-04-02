package com.tutorials.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorials.models.Chat;
import com.tutorials.models.Message;
import com.tutorials.models.User;
import com.tutorials.repository.ChatRepository;
import com.tutorials.repository.MessageRepository;

@Service
public class MessageServiceImplementation implements MessageService{
	@Autowired
	private MessageRepository messageRepo;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatRepository chatRepo;
	@Override
	public Message createMessage(User user, Integer chatId, Message message) throws Exception {
		Message newMsg = new Message();
		
		Chat chat = chatService.findChatById(chatId);
		newMsg.setChat(chat);
		newMsg.setContent(message.getContent());
		newMsg.setImage(message.getImage());
		newMsg.setUser(user);
		newMsg.setTimeStamp(LocalDateTime.now());
		
		Message savedMessage =  messageRepo.save(newMsg);
		chat.getMessages().add(savedMessage);
		chatRepo.save(chat);
		return savedMessage;
	}

	@Override
	public List<Message> findChatsMessages(Integer chatId) throws Exception {
		Chat chat = chatService.findChatById(chatId);
		return messageRepo.findByChatId(chatId);
	}

	

}
