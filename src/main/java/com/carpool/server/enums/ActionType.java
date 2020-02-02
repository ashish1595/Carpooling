package com.carpool.server.enums;

public enum ActionType {

	ACCEPT(1), REJECT(2);
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private ActionType(int type) {
		this.type = type;
	}
}
