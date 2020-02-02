package com.carpool.server.entity;

import java.util.Map;

public class LdapAuthPOJO {
	
	boolean isAuthenticated;
	Map<String,Object> additionalAttributes;
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	public Map<String, Object> getAdditionalAttributes() {
		return additionalAttributes;
	}
	public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}
	
	@Override
	public String toString() {
		return "LDAPAuth [isAuthenticated=" + isAuthenticated + ", additionalAttributes=" + additionalAttributes + "]";
	}
	
}
