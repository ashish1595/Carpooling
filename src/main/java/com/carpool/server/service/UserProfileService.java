package com.carpool.server.service;

import com.carpool.server.dto.UserProfileDTO;
import com.carpool.server.exception.UtilityException;

public interface UserProfileService {

	String saveUserDetails(UserProfileDTO userProfile) throws UtilityException;

	UserProfileDTO getUserDetails(String olmId) throws UtilityException;

}
