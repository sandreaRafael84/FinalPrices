package com.grupobnc.itx.checkprices.application.service;

import com.grupobnc.itx.checkprices.application.mapper.PriceMapper;
import com.grupobnc.itx.checkprices.application.port.in.CheckPriceCommand;
import com.grupobnc.itx.checkprices.application.port.in.PriceCommand;
import com.grupobnc.itx.checkprices.application.port.in.PricePort;
import com.grupobnc.itx.checkprices.application.port.out.PriceCheckedResult;
import com.grupobnc.itx.checkprices.common.UseCase;
import com.grupobnc.itx.checkprices.domain.Prices;
import com.grupobnc.itx.checkprices.domain.repository.PriceRepository;
import com.grupobnc.itx.checkprices.dto.out.PriceResultDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@UseCase
@Slf4j
public class DomainPriceService implements PricePort {

	private PriceRepository priceRepository;

	public DomainPriceService(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	@Override
	public Optional<PriceCheckedResult> findFinalPricesByApplicationDate(CheckPriceCommand checkPriceCommand) {
		Optional<Prices> optPrices = priceRepository.findFinalPricesByApplicationDate(checkPriceCommand);
		log.info(
				"Finalize execution operation  DomainPriceService::getApplicablePrice  by product {idProduct} and applicationDate {date} whose result was {result} ",
				checkPriceCommand.productId(), checkPriceCommand.applicationDate(),
				optPrices.isPresent() ? "success" : "fail");
		return PriceMapper.mapperToPriceCheckedResult(optPrices);

	}

	@Override
	public List<Prices> findListPriceAvailableOfProduct(Long brandId, Long productId) {
		return priceRepository.findListPriceAvailableOfProduct(brandId, productId);
	}

	@Override
	public Optional<PriceResultDTO> save(PriceCommand pricesCmd) {
		return PriceMapper.mapperToPriceResult(priceRepository.save(pricesCmd));
	}

	@Override
	public void deleteById(Long id) {
		priceRepository.deleteById(id);
	}

}
