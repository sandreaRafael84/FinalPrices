package com.grupobnc.itx.checkprices.dto.out;

import java.util.List;

public record ApiPriceResponseDTO<T>(ApiMetadata metadata, T data, ApiError error) {

	public static <T> ApiPriceResponseDTO<T> fromSuccess(ApiMetadata metadata, T data) {
		return new ApiPriceResponseDTO(metadata, data, null);
	}

	public static <T> ApiPriceResponseDTO<List<T>> fromSuccess(ApiMetadata metadata, List<T> data) {
		return new ApiPriceResponseDTO(metadata, data, null);
	}

	public static <T> ApiPriceResponseDTO<T> fromFailure(ApiMetadata metadata, ApiError error) {
		return new ApiPriceResponseDTO(metadata, null, error);
	}

	public boolean isSuccess() {
		return data != null;
	}

	public T getData() {
		return data;
	}

	public ApiError getError() {
		return error;
	}
}
