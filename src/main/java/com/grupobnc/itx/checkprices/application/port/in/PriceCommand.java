package com.grupobnc.itx.checkprices.application.port.in;

import com.grupobnc.itx.checkprices.domain.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceCommand(@NotNull(message = "productId cannot be null") Long productId,
		@NotNull(message = "brandId cannot be null") Long brandId,
		@NotNull(message = "start Date cannot be null") LocalDateTime startDate,
		@NotNull(message = "End Date cannot be null") LocalDateTime endDate,
		@NotNull(message = "priceList cannot be null") int priceList,
		@NotNull(message = "priority cannot be null") int priority,
		@NotNull(message = "Price cannot be null") @Positive(message = "Price must be positive") BigDecimal price,
		Currency curr) {

	public static PriceCommand of(Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
			int priceList, int priority, BigDecimal price, Currency curr) {
		return new PriceCommand(productId, brandId, startDate, endDate, priceList, priority, price, curr);
	}

}