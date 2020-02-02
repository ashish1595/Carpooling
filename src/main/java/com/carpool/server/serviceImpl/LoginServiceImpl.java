package com.carpool.server.serviceImpl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpool.server.exception.ResponseCode;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.repository.UserProfileRepository;
import com.carpool.server.service.ILDAPService;
import com.carpool.server.service.LoginService;
import com.carpool.server.utils.CommonUtility;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private ILDAPService lDAPService;

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class.getName());


	@Override
	public HashMap<String, String> login(String olmId, String password) throws UtilityException {
		logger.info("login for olm :: " + olmId + " started...");
		HashMap<String, String> responseMap = null;
		if(! (CommonUtility.isValidString(olmId) && CommonUtility.isValidString(password) && 
				(olmId.charAt(0) == 'B' || olmId.charAt(0) == 'b') && (olmId.length() == 8)))
			throw new UtilityException(ResponseCode.USER_DATA_NOT_FOUND_IN_REQUEST);

		responseMap = lDAPService.authenticate(olmId, password);
		logger.info("login for olm :: " + olmId + " responseMap :: "+ responseMap);

		if(CommonUtility.isNullObject(responseMap)) throw new UtilityException(ResponseCode.LDAP_AUTH_FAIL);

		Boolean userProfileExists = userProfileRepository.existsByOlmId(olmId);
		responseMap.put("profile", userProfileExists.toString());
		logger.info("login for olm :: " + olmId + " ended...");
		return responseMap;
	}
}