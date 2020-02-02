package com.carpool.server.dto;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="Notification")
public class NotificationDTO {

	@Id
	private String _id;
	private String targetUserId;
	private String message;
	private Map<String, String> additionalInfo;
	private Integer type;
	private Integer readFlag;
	private Integer status;
	private Date createdDate;
}