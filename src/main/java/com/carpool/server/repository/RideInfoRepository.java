package com.carpool.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carpool.server.dto.RideInfoDTO;

@Repository
public interface RideInfoRepository extends MongoRepository<RideInfoDTO, String> {
	
	List<RideInfoDTO> findByOriginLatitudeAndOriginLongitudeAndTotalDistanceGreaterThanAndStartTimeGreaterThan(
			@Param("originLatitude") Double originLatitude, @Param("originLongitude") Double originLongitude, @Param("totalDistance") Long totalDistance,
			@Param("startTime") Date startTime);
	
	List<RideInfoDTO> findByDestinationLatitudeAndDestinationLongitudeAndTotalDistanceGreaterThanAndStartTimeGreaterThan(
			@Param("destinationLatitude") Double destinationLatitude, @Param("destinationLongitude") Double destinationLongitude, @Param("totalDistance") Long totalDistance,
			@Param("startTime") Date startTime);
}