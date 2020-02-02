package com.carpool.server.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.carpool.server.dto.UserProfileDTO;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfileDTO, String>{

	Boolean existsByOlmId(String olmId);
	List<UserProfileDTO> findByOlmId(String olmId);
}