package com.carpool.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carpool.server.dto.UserProfileDTO;
import com.carpool.server.entity.RestResponse;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.service.UserProfileService;
import com.carpool.server.utils.RestUtils;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<RestResponse<String>> saveUserDetails(@RequestBody UserProfileDTO userProfile) throws UtilityException {
		return RestUtils.successResponse(userProfileService.saveUserDetails(userProfile));
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<RestResponse<UserProfileDTO>> getUserDetails(
			@RequestHeader("olmId") String olmId) throws UtilityException {
		return RestUtils.successResponse(userProfileService.getUserDetails(olmId));
	}
}