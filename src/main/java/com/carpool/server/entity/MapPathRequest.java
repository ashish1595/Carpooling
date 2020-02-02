package com.carpool.server.entity;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.Data;

@Data
public class MapPathRequest {

	private Double originLatitude;
	private Double originLongitude;
	private Double destinationLatitude;
	private Double destinationLongitude;
	private GeoJsonPoint geoJsonPoint;
	private String originAddress;
	private String destinationAddress;
}