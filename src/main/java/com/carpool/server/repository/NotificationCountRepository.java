package com.carpool.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carpool.server.dto.NotificationCountDTO;

@Repository
public interface NotificationCountRepository extends MongoRepository<NotificationCountDTO, String>{

}