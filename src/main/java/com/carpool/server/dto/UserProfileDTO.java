package com.carpool.server.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.carpool.server.entity.LocationEntity;

import lombok.Data;

@Data
@Document(collection="userProfile")
public class UserProfileDTO {

	@Id
	private String _id;
	private String olmId;
	private LocationEntity officeAddress;
	private LocationEntity homeAddress;
	private Integer riderType; //Passenger or Car Owner
	private String startTimeToOffice;
	private String startTimeToHome;
	private String carModel;
	private String carNumber;
	private Integer seats;
}