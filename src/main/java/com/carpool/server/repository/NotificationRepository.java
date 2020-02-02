package com.carpool.server.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carpool.server.dto.NotificationDTO;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationDTO, String>{
	
	List<NotificationDTO> findByTargetUserId(String targetUserId);
}
