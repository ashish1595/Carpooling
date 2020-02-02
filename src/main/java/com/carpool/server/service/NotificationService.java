package com.carpool.server.service;

import java.util.List;

import com.carpool.server.dto.NotificationDTO;
import com.carpool.server.dto.NotificationCountDTO;
import com.carpool.server.exception.UtilityException;

public interface NotificationService {

	List<NotificationDTO> getNotifications(String userId) throws UtilityException;

	Boolean changeReadFlag(String notificationId) throws UtilityException;

	NotificationCountDTO getNotificationCount(String userId) throws UtilityException;
}