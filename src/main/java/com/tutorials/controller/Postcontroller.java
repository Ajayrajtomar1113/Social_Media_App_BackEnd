package com.tutorials.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.tutorials.models.Post;
import com.tutorials.models.User;
import com.tutorials.response.ApiResponse;
import com.tutorials.service.PostService;
import com.tutorials.service.UserService;

@RestController
public class Postcontroller {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // CREATE POST
    @PostMapping("/api/post")
    public ResponseEntity<Post> createPost(
            @RequestBody Post post,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        Post createdPost = postService.createNewPost(post, reqUser.getId());

        return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
    }

    //  DELETE POST
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwt(jwt);

        String msg = postService.deletePost(postId, user.getId());

        messagingTemplate.convertAndSend("/topic/delete-post", postId);

        return ResponseEntity.ok(new ApiResponse(msg, true));
    }

    // FIND POST BY ID
    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {

        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }

    // USER POSTS
    @GetMapping("/api/posts/user")
    public ResponseEntity<List<Post>> findUsersPost(@RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserByJwt(jwt);
        List<Post> posts = postService.findPostByUserId(reqUser.getId());

        return new ResponseEntity<>(posts, HttpStatus.ACCEPTED);
    }

    //  ALL POSTS
    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> findAllPost() {

        List<Post> posts = postService.findAllPost();
        return ResponseEntity.ok(posts);
    }

    // SAVE POST
    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<User> savedPostHandler(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        User user = postService.savedPost(postId, reqUser.getId());

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    //LIKE POST
    @PutMapping("/api/post/like/{postId}")
    public ResponseEntity<Post> likedPostHandler(
            @PathVariable Integer postId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.likePost(postId, reqUser.getId());

        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }
}