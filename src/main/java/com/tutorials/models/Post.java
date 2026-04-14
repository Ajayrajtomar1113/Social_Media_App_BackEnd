//
//
//package com.tutorials.models;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.hibernate.annotations.CreationTimestamp;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import jakarta.persistence.*;
//
//@Entity
//public class Post {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    private String caption;
//    private String image;
//    private String video;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    @JsonIgnoreProperties({"savedPost","followers","followings","password","posts","reels"})
//    private User user;
//
//    @ManyToMany
//    @JoinTable(
//        name = "post_likes",
//        joinColumns = @JoinColumn(name = "post_id"),
//        inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private List<User> liked = new ArrayList<>();
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnoreProperties("post")
//    private List<Comment> comments = new ArrayList<>();
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    public Post() {}
//
//    // GETTERS & SETTERS
//
//    public Integer getId() { return id; }
//    public void setId(Integer id) { this.id = id; }
//
//    public String getCaption() { return caption; }
//    public void setCaption(String caption) { this.caption = caption; }
//
//    public String getImage() { return image; }
//    public void setImage(String image) { this.image = image; }
//
//    public String getVideo() { return video; }
//    public void setVideo(String video) { this.video = video; }
//
//    public User getUser() { return user; }
//    public void setUser(User user) { this.user = user; }
//
//    public List<User> getLiked() { return liked; }
//    public void setLiked(List<User> liked) { this.liked = liked; }
//
//    public List<Comment> getComments() { return comments; }
//    public void setComments(List<Comment> comments) { this.comments = comments; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
//}


package com.tutorials.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String caption;
    private String image;
    private String video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({
        "savedPost",
        "followers",
        "followings",
        "password",
        "posts",
        "reels",
        "liked"
    })
    private User user;

    @ManyToMany
    @JoinTable(
        name = "post_likes",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({
        "savedPost",
        "followers",
        "followings",
        "password",
        "posts",
        "reels",
        "liked"
    })
    private List<User> liked = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("post")
    private List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Post() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getLiked() {
        return liked;
    }

    public void setLiked(List<User> liked) {
        this.liked = liked;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}