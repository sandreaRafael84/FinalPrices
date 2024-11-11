package com.grupobnc.itx.finalprice.application.port.out;

import com.grupobnc.itx.finalprice.domain.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceCheckedResult(Long productId, Long supplyChainId, int rateToApply,
		LocalDateTime applicationDateStart, LocalDateTime applicationDateEnd, BigDecimal finalPrice, Currency cur) {

	public static PriceCheckedResult of(Long productId, Long supplyChainId, int rateToApply,
			LocalDateTime applicationDateStart, LocalDateTime applicationDateEnd, BigDecimal finalPrice, Currency cur) {
		return new PriceCheckedResult(productId, supplyChainId, rateToApply, applicationDateStart, applicationDateEnd,
				finalPrice, cur);
	}
}
