package com.grupobnc.itx.checkprices.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Utility {

	static String SPEC_VERSION = "1.0";

	static String OP_CREATE_UPDATE_PRICE = "createOrUpdatePrice";
	static String OP_DELETE_PRICE = "deletePrice";
	static String OP_FIND_FINAL_PRICE = "findFinalPrice";
	static String OP_FIND_LIST_PRICE_AVAILABLE_PRODUCT = "findListPriceAvailableOfProduct";

	static String FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
	static String BUSINESS_ERROR_MISSING = "Invalid incoming request due to ";
	static String BUSINESS_ERROR_DATE = "Invalid date format, please use the following format 'YYYY-MM-ddT00:00:00";
	static String BUSINESS_ERROR_NOT_FOUND = "Product ID not found";
	static String BUSINESS_ERROR_CODE = "BUSINESS ERROR";
	static String TECHNICAL_ERROR_CODE = "TECHNICAL ERROR";

	static LocalDateTime parseDate(String strDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_PATTERN);
		return LocalDateTime.parse(strDate, formatter);
	}

	static String convertDateToString(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_PATTERN);
		return localDateTime.format(formatter);
	}

}
