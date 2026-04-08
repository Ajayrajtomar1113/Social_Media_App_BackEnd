//package com.tutorials.models;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.OneToMany;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//public class Chat {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer id;
//	
//	private String chat_name;
//	
//	private String chat_image;
//	
//	@ManyToMany
//	 @JsonIgnoreProperties({"savedPost","followers","followings","password"})
//    private List<User> users = new ArrayList<>();
//
//	private LocalDateTime timeStamp;
//	
//	@OneToMany(mappedBy = "chat")
//	private List<Message> messages = new ArrayList<>();
//}

package com.tutorials.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToMany
    @JoinTable(
        name = "chat_users",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    private LocalDateTime timeStamp;
    
    public Chat() {}


    
    public LocalDateTime getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}



	public Integer getId() 
    {
    	return id; 
    	}
    public void setId(Integer id) 
    {
    	this.id = id; 
    	}

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
}
