package com.grupobnc.itx.finalprices.dto.out;

import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.grupobnc.itx.finalprices.util.Utility.SPEC_VERSION;
import static com.grupobnc.itx.finalprices.util.Utility.convertDateToString;

public record ApiMetadata(String specVersion, String operation, String id, String time, String dataContentType) {

	public static ApiMetadata of(String operation) {
		return new ApiMetadata(SPEC_VERSION, operation, buildUuidFromCurrentReq(),
				convertDateToString(LocalDateTime.now()), MediaType.APPLICATION_JSON.toString());
	}

	private static String buildUuidFromCurrentReq() {
		return UUID.randomUUID().toString();
	}

}
