package com.carpool.server.service;

import java.util.HashMap;

import com.carpool.server.exception.UtilityException;

public interface ILDAPService {

	HashMap<String, String> authenticate(String olmId, String password) throws UtilityException;

}
