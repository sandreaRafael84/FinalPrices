package com.grupobnc.itx.finalprices.dto.out;

import com.grupobnc.itx.finalprices.domain.Currency;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResultDTO(Long id, Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
		int priceList, int priority, @NotNull(message = "Price cannot be null") BigDecimal price, Currency curr) {

	public static PriceResultDTO of(Long id, Long productId, Long brandId, LocalDateTime startDate,
			LocalDateTime endDate, int priceList, int priority, BigDecimal price, Currency curr) {
		return new PriceResultDTO(id, productId, brandId, startDate, endDate, priceList, priority, price, curr);
	}

}
