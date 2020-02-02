package com.carpool.server.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "officeAddress")
public class OfficeAddressDTO {

	@Id
	private String _id;
	private String officeName;
	private String officeAddress;
	private Double latitude;
	private Double longitude;
	private Integer status;
}