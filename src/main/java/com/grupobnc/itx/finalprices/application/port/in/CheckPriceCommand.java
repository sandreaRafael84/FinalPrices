package com.grupobnc.itx.finalprices.application.port.in;

import java.time.LocalDateTime;

public record CheckPriceCommand(Long productId, Long brandId, LocalDateTime applicationDate) {

	public static CheckPriceCommand of(Long productId, Long brandId, LocalDateTime applicationDate) {
		return new CheckPriceCommand(productId, brandId, applicationDate);
	}
}
