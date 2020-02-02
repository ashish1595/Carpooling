package com.carpool.server.entity;

import java.util.List;

import lombok.Data;

@Data
public class RequestRideData {

	private String userId;
	private String name;
	private List<RideData> rideDataList;
}