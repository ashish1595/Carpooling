package com.carpool.server.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpool.server.dto.UserProfileDTO;
import com.carpool.server.exception.ResponseCode;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.repository.UserProfileRepository;
import com.carpool.server.service.UserProfileService;
import com.carpool.server.utils.CommonUtility;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Override
	public String saveUserDetails(UserProfileDTO userProfile) throws UtilityException {
		userProfileRepository.save(userProfile);
		return "User Profile saved successfully!";
	}

	@Override
	public UserProfileDTO getUserDetails(String olmId) throws UtilityException {
		List<UserProfileDTO> userDetails = userProfileRepository.findByOlmId(olmId);
		if (! CommonUtility.isValidList(userDetails))
			throw new UtilityException(ResponseCode.USER_DATA_NOT_FOUND_IN_RESPONSE);
		return userDetails.get(0);
	}
}