package com.tutorials.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tutorials.models.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{

	@Query("select p from Post p where p.user.id=:userId")
	List<Post> findPostByUserId(Integer userId);
	
	@Modifying
	@Query(value = "DELETE FROM post_liked WHERE post_id = :postId", nativeQuery = true)
	void deleteLikesByPostId(@Param("postId") Integer postId);
}
