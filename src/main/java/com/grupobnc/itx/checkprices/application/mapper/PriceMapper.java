package com.grupobnc.itx.checkprices.application.mapper;

import com.grupobnc.itx.checkprices.application.port.out.PriceCheckedResult;
import com.grupobnc.itx.checkprices.domain.Prices;
import com.grupobnc.itx.checkprices.dto.out.PriceResultDTO;

import java.util.Optional;

public interface PriceMapper {

	static Optional<PriceResultDTO> mapperToPriceResult(Optional<Prices> optPrices) {
		if (optPrices.isPresent()) {
			return buildPriceResult(optPrices.get());
		}
		else {
			return Optional.empty();
		}
	}

	private static Optional<PriceResultDTO> buildPriceResult(Prices prices) {
		return Optional.of(PriceResultDTO.of(prices.getId(), prices.getProductId(), prices.getBrand(),
				prices.getStartDate(), prices.getEndDate(), prices.getPriceList(), prices.getPriority(),
				prices.getPrice(), prices.getCurr()));
	}

	static Optional<PriceCheckedResult> mapperToPriceCheckedResult(Optional<Prices> optPrices) {
		if (optPrices.isPresent()) {
			return buildPriceCheckedResult(optPrices.get());
		}
		else {
			return Optional.empty();
		}
	}

	private static Optional<PriceCheckedResult> buildPriceCheckedResult(Prices prices) {
		return Optional.of(PriceCheckedResult.of(prices.getProductId(), prices.getBrand(), prices.getPriceList(),
				prices.getStartDate(), prices.getEndDate(), prices.getPrice(), prices.getCurr()));
	}

}
