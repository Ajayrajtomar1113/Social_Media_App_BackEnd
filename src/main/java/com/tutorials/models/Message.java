//package com.tutorials.models;
//
//
//import java.time.LocalDateTime;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Message {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer id;
//	private String content;
//	private String image;
//	
//		
//	@ManyToOne
//    @JsonIgnoreProperties({"savedPost","followers","followings","password"})
//    private User user;
//	
//	@JsonIgnore
//	@ManyToOne
//	private Chat chat;
//	private LocalDateTime timeStamp;
//}

package com.tutorials.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;
    private String image;

    // 🔥 Sender
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"savedPost","followers","followings","password","posts","reels"})
    private User user;

    // 🔥 Chat reference
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private LocalDateTime timeStamp;

    public Message() {}

    // GETTERS & SETTERS

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Chat getChat() { return chat; }
    public void setChat(Chat chat) { this.chat = chat; }

    public LocalDateTime getTimeStamp() { return timeStamp; }
    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }
}