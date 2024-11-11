package com.grupobnc.itx.finalprice.dto.in;

import jakarta.validation.constraints.NotNull;

public record AplicablePriceReqDTO(@NotNull(message = "productId  cannot be null") Long productId,
		@NotNull(message = "brandId cannot be null") Long brandId,
		@NotNull(message = "applicationDate cannot be null") String applicationDate) {

	public static AplicablePriceReqDTO of(Long productId, Long brandId, String applicationDate) {
		return new AplicablePriceReqDTO(productId, brandId, applicationDate);
	}
}
