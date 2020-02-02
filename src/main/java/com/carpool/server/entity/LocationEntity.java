package com.carpool.server.entity;

import lombok.Data;

@Data
public class LocationEntity {

	private String id;
	private String address;
	private Double latitude;
	private Double longitude;
}
