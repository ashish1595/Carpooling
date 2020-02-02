package com.carpool.server.entity;

import lombok.Data;

@Data
public class LoginRequestData {

	private String olmId;
	private String password;
	private String deviceId;
	private Object additionalInfo;
}
