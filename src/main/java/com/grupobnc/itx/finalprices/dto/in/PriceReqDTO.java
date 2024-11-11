package com.grupobnc.itx.finalprices.dto.in;

import com.grupobnc.itx.finalprices.domain.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PriceReqDTO(@NotNull(message = "productId cannot be null") Long productId,
		@NotNull(message = "start Date cannot be null") Long brandId,
		@NotNull(message = "start Date cannot be null") String startDate,
		@NotNull(message = "End Date cannot be null") String endDate,
		@NotNull(message = "Applicable price rate cannot be null") int priceList,
		@NotNull(message = "Priority cannot be null") int priority,
		@NotNull(message = "Price cannot be null") @Positive(message = "Price must be positive") BigDecimal price,
		@NotNull(message = "Price cannot be null") Currency curr) {

	public static PriceReqDTO of(Long productId, Long brandId, String startDate, String endDate, int priceList,
			int priority, BigDecimal price, Currency curr) {
		return new PriceReqDTO(productId, brandId, startDate, endDate, priceList, priority, price, curr);
	}

}
