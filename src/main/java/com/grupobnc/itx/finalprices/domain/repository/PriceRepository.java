package com.grupobnc.itx.finalprices.domain.repository;

import com.grupobnc.itx.finalprices.application.port.in.CheckPriceCommand;
import com.grupobnc.itx.finalprices.application.port.in.PriceCommand;
import com.grupobnc.itx.finalprices.domain.Prices;

import java.util.List;
import java.util.Optional;

public interface PriceRepository {

	Optional<Prices> findFinalPricesByApplicationDate(CheckPriceCommand checkPriceCommand);

	List<Prices> findListPriceAvailableOfProduct(Long brandId, Long productId);

	Optional<Prices> save(PriceCommand pricesCmd);

	void deleteById(Long id);

}
