package com.grupobnc.itx.finalprices.domain;

public enum Currency {

	USD("United States Dollar"), EUR("Euro"), GBP("British Pound Sterling"), JPY("Japanese Yen"), CHF("Swiss Franc");

	private final String description;

	Currency(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
