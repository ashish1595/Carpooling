package com.carpool.server.entity;

import lombok.Data;

@Data
public class ActionRideData {

	private Integer action;
	private String rideId;
	private String riderId;
	private String notificationId;
}