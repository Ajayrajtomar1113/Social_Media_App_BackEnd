package com.tutorials.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tutorials.models.Chat;
import com.tutorials.models.User;

public interface ChatRepository extends JpaRepository<Chat,Integer>{

	public List<Chat> findByUsers_Id(Integer userId);

	
	@Query("select c from Chat c where :user Member of c.users And :reqUser Member of c.users")
	public Chat findChatByUsersId(@Param("user") User user, @Param("reqUser") User reqUser);
}
