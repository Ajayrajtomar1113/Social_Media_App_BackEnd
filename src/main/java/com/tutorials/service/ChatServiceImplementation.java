package com.tutorials.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorials.models.Chat;
import com.tutorials.models.User;
import com.tutorials.repository.ChatRepository;

@Service
public class ChatServiceImplementation implements ChatService{

	@Autowired
	ChatRepository chatRepo;
	
	@Override
	public Chat createChat(User reqUser, User user) {
		Chat isExist = chatRepo.findChatByUsersId(user, reqUser);
		
		if(isExist != null) {
			return isExist;
		}
		Chat chat = new Chat();
		chat.getUsers().add(user); 
		chat.getUsers().add(reqUser);
		chat.setTimeStamp(LocalDateTime.now());
 		return chatRepo.save(chat);
	}

	@Override
	public Chat findChatById(Integer chatId) throws Exception {
		Optional<Chat> opt = chatRepo.findById(chatId);
		if(opt.isEmpty()) {
			throw new Exception("Chat not found with id "+chatId);
		}
		return opt.get();
	}

	@Override
	public List<Chat> findUserChats(Integer userId) {
		
		return chatRepo.findByUsers_Id(userId);
	}

}
