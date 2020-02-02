package com.carpool.server.utils;

public interface Constants {
	Integer SUCCESS_STATUS = 200;
	Integer FAILURE_STATUS = 101;
	String SUCCESS_STATUS_MESAGE = "ok";
	Integer ACTIVE_STATUS = 1;
	Integer INACTIVE_STATUS = 0;
	Integer EXPIRY_DAYS = 15;
	String SECURITY_CERTIFICATE = null;
	
	interface APP {
		String APP_NAME = "carpool";
	}
	
	interface VALUE {
		String SERVER_KEY = "123456789";
	}
	
	interface HTTP_METHOD {
		String GET = "GET";
		String POST = "POST";
	}
	
	interface URI {
		String GET = "/";
		String PROFILE = "/profile";
		String LOAD_ALL = "/properties/load";
		String GET_ALL = "/properties";
		String CHECK_STATUS = "/status";
		String LOGIN = "/login";
		String OFFICE = "/office";
		String GOOGLE_PATH = "/path";
		String DRIVER = "/driver";
		String RIDER = "/rider";
		String REQUEST = "/request";
		String REQUEST_RIDE = "/select/ride";
		String ACTION_RIDE = "/action/ride";
		String VERIFY_EMAIL = "/verify/email";
		String REWARD = "/reward";
	}
	
	interface Headers {
		String APPLICATION_ID = "APPLICATION_ID";
		String APPLICATION_NAME = "APPLICATION_NAME";
		String APP_VERSION = "APP_VERSION";
		String USER_AGENT = "USER_AGENT";
		String DEVICE_ID = "DEVICE_ID";
		String MOBILE_NUMBER = "MOBILE_NUMBER";
		String EMAIL_ID = "EMAIL_ID";
		String USER_ID = "USER_ID";
		String TRIP_ID = "tripId";
		String AUTH_TOKEN = "AUTH_TOKEN";
		String TOKEN_ID = "AUTH_TOKEN";
		String CLIENT_APP_VERSION = "CLIENT_APP_VERSION";
		String SERVER_KEY = "SERVER_KEY";
		String KEY = "key";
		String DATA = "data";
		String ContentType = "Content-Type";
		String AppJson = "application/json";
		String UTF8 = "UTF-8";
		String STATUS = "status";
		Integer SUCCESS_CODE = 200;
	}
}
