package com.carpool.server.enums;

public enum RiderType {
	
	Driver(1), RIDER(2);
	
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	RiderType(int type) {
		this.type = type;
	}
}