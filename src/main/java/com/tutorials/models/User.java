//
//package com.tutorials.models;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    private String firstName;
//    private String lastName;
//
//    @Column(unique = true)
//    private String email;
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private String password;
//
//    private String gender;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @ManyToMany
//    private List<Post> savedPost = new ArrayList<>();
//
//    @ElementCollection
//    private List<Integer> followers = new ArrayList<>();
//
//    @ElementCollection
//    private List<Integer> followings = new ArrayList<>();
//
//    // 🔥 IMPORTANT RELATIONS
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Post> posts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Reels> reels = new ArrayList<>();
//
//    public User() {}
//
//    // GETTERS & SETTERS
//
//    public Integer getId() { return id; }
//    public void setId(Integer id) { this.id = id; }
//
//    public String getFirstName() { return firstName; }
//    public void setFirstName(String firstName) { this.firstName = firstName; }
//
//    public String getLastName() { return lastName; }
//    public void setLastName(String lastName) { this.lastName = lastName; }
//
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public String getGender() { return gender; }
//    public void setGender(String gender) { this.gender = gender; }
//
//    public Role getRole() { return role; }
//    public void setRole(Role role) { this.role = role; }
//
//    public List<Post> getSavedPost() { return savedPost; }
//    public void setSavedPost(List<Post> savedPost) { this.savedPost = savedPost; }
//
//    public List<Integer> getFollowers() { return followers; }
//    public void setFollowers(List<Integer> followers) { this.followers = followers; }
//
//    public List<Integer> getFollowings() { return followings; }
//    public void setFollowings(List<Integer> followings) { this.followings = followings; }
//
//    public List<Post> getPosts() { return posts; }
//    public void setPosts(List<Post> posts) { this.posts = posts; }
//
//    public List<Reels> getReels() { return reels; }
//    public void setReels(List<Reels> reels) { this.reels = reels; }
//}


package com.tutorials.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
        name = "saved_posts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> savedPost = new ArrayList<>();
    @ElementCollection
    private List<Integer> followers = new ArrayList<>();

    @ElementCollection
    private List<Integer> followings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore   // 🔥 MOST IMPORTANT (loop break)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reels> reels = new ArrayList<>();

    public User() {}


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public List<Post> getSavedPost() { return savedPost; }
    public void setSavedPost(List<Post> savedPost) { this.savedPost = savedPost; }

    public List<Integer> getFollowers() { return followers; }
    public void setFollowers(List<Integer> followers) { this.followers = followers; }

    public List<Integer> getFollowings() { return followings; }
    public void setFollowings(List<Integer> followings) { this.followings = followings; }

    public List<Post> getPosts() { return posts; }
    public void setPosts(List<Post> posts) { this.posts = posts; }

    public List<Reels> getReels() { return reels; }
    public void setReels(List<Reels> reels) { this.reels = reels; }
}