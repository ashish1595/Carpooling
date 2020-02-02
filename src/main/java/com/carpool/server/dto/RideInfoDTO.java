package com.carpool.server.dto;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection="rideInfo")
@Data
public class RideInfoDTO {

	@Id
	private String _id;
	private String userId;
	private String name;
	private String mobileNumber;
	private Date startTime;
	private Double originLatitude;
	private Double originLongitude;
	private Double destinationLatitude;
	private Double destinationLongitude;
	private String startAddress; //needs to send id in case of office address
	private String endAddress; //needs to send id in case of office address
	private String selectedPolylineRoute;
	private Long totalDistance;
	private String journeyType;
	private String availableSeats;
	@Transient
	private String selectedKey;
	@Transient
	private Long startTimeLong;
}