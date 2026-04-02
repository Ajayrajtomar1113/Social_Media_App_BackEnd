package com.tutorials.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.tutorials.models.Message;
import com.tutorials.models.User;
import com.tutorials.service.MessageService;
import com.tutorials.service.UserService;

@RestController
public class MessageController {

@Autowired
private MessageService messageService;

@Autowired
private UserService userService;

// 🔥 ADD THIS
@Autowired
private SimpMessagingTemplate messagingTemplate;

@PostMapping("/api/message/chat/{chatId}")
public Message createMessage( @RequestBody Message req, @RequestHeader("Authorization") String jwt,@PathVariable Integer chatId) throws Exception {

    User user = userService.findUserByJwt(jwt);

    Message message = messageService.createMessage(user, chatId, req);


    messagingTemplate.convertAndSend(
            "/topic/chat/" + chatId,
            message
    );

    return message;
}

@GetMapping("/api/message/chat/{chatId}")
public List<Message> findChatMessages(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Integer chatId
) throws Exception {

    User user = userService.findUserByJwt(jwt);

    return messageService.findChatsMessages(chatId);
}

}

