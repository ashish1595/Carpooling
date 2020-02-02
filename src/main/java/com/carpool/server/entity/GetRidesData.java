package com.carpool.server.entity;

import lombok.Data;

@Data
public class GetRidesData {

	private Double originLatitude;
	private Double originLongitude;
	private Double destinationLatitude;
	private Double destinationLongitude;
	private String isOptimised;
	private Long startTime;
	private String journeyType;
}
