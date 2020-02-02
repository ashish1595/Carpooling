package com.carpool.server.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="notificationCount")
public class NotificationCountDTO {

	@Id
	private String _id;
	private String userId;
	private String count;
}