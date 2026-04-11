
package com.tutorials.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Reels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private String video;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"savedPost","followers","followings","password","posts","reels"})
    private User user;

    public Reels() {}

    // GETTERS & SETTERS

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}