package com.carpool.server.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.carpool.server.entity.RestResponse;
import com.carpool.server.exception.UtilityException;
import com.carpool.server.service.LoginService;
import com.carpool.server.utils.Constants;
import com.carpool.server.utils.RestUtils;

@RestController
@RequestMapping(Constants.URI.LOGIN)
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<RestResponse<HashMap<String, String>>> login(
			@RequestHeader("olmId") String olmId,
			@RequestHeader("password") String password) throws UtilityException {
		return RestUtils.successResponse(loginService.login(olmId, password));
	}
}