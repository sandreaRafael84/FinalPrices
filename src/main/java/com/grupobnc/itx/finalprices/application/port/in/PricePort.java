package com.grupobnc.itx.finalprices.application.port.in;

import com.grupobnc.itx.finalprices.application.port.out.PriceCheckedResult;
import com.grupobnc.itx.finalprices.domain.Prices;
import com.grupobnc.itx.finalprices.dto.out.PriceResultDTO;

import java.util.List;
import java.util.Optional;

public interface PricePort {

	List<Prices> findListPriceAvailableOfProduct(Long brandId, Long productId);

	Optional<PriceCheckedResult> findFinalPricesByApplicationDate(CheckPriceCommand checkPriceCommand);

	Optional<PriceResultDTO> save(PriceCommand pricesCmd);

	void deleteById(Long id);

}
