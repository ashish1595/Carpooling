package com.carpool.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carpool.server.dto.NotificationDTO;
import com.carpool.server.dto.NotificationCountDTO;
import com.carpool.server.entity.RestResponse;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.service.NotificationService;
import com.carpool.server.utils.RestUtils;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<RestResponse<List<NotificationDTO>>> getNotifications(
			@RequestParam("userId") String userId) throws UtilityException {
		return RestUtils.successResponse(notificationService.getNotifications(userId));
	}

	@RequestMapping(value = "/seen", method=RequestMethod.GET)
	public ResponseEntity<RestResponse<Boolean>> changeReadFlag(
			@RequestParam("notificationId") String notificationId) throws UtilityException {
		return RestUtils.successResponse(notificationService.changeReadFlag(notificationId));
	}

	@RequestMapping(value = "/count", method=RequestMethod.GET)
	public ResponseEntity<RestResponse<NotificationCountDTO>> getNotificationCount(
			@RequestParam("userId") String userId) throws UtilityException {
		return RestUtils.successResponse(notificationService.getNotificationCount(userId));
	}
}