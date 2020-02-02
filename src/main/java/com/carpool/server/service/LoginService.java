package com.carpool.server.service;

import java.util.HashMap;

import com.carpool.server.exception.UtilityException;

public interface LoginService {

	HashMap<String, String> login(String olmId, String password) throws UtilityException;
}
