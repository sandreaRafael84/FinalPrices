package com.grupobnc.itx.checkprices.infrastructure.repository;

import com.grupobnc.itx.checkprices.application.port.in.CheckPriceCommand;
import com.grupobnc.itx.checkprices.application.port.in.PriceCommand;
import com.grupobnc.itx.checkprices.common.PersistenceAdapter;
import com.grupobnc.itx.checkprices.domain.Prices;
import com.grupobnc.itx.checkprices.domain.repository.PriceRepository;
import com.grupobnc.itx.checkprices.infrastructure.entity.PricesEntity;
import com.grupobnc.itx.checkprices.infrastructure.mapper.PriceMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.grupobnc.itx.checkprices.util.Utility.OP_CREATE_UPDATE_PRICE;

@PersistenceAdapter
@Slf4j
public class PriceRepositoryAdapter implements PriceRepository {

	@Autowired
	private JpaPriceRepository jpaPriceRepository;

	@Override
	@Transactional
	public Optional<Prices> findFinalPricesByApplicationDate(CheckPriceCommand checkPriceCommand) {
		Optional<PricesEntity> optEntityPrices = jpaPriceRepository.findFinalPricesByApplicationDate(
				checkPriceCommand.applicationDate(), checkPriceCommand.productId(), checkPriceCommand.brandId());
		log.info(
				"Finalize execution operation  PriceRepositoryAdapter::findFinalPricesByApplicationDate  by product {idProduct} and applicationDate {date} whose result was {result}",
				checkPriceCommand.productId(), checkPriceCommand.applicationDate(),
				optEntityPrices.isPresent() ? "success" : "fail");
		return buildDomainPrices(optEntityPrices);
	}

	@Override
	@Transactional
	public List<Prices> findListPriceAvailableOfProduct(Long brandId, Long productId) {
		List<PricesEntity> pricesEntityList = jpaPriceRepository.findListPriceAvailableOfProduct(productId, brandId);
		log.info(
				"Finalize execution operation  PriceRepositoryAdapter::findListPriceAvailableOfProduct  by product {idProduct} and brandId {brandId} whose result was {result} rowAvailablePrices",
				productId, brandId, pricesEntityList.size());
		return pricesEntityList.stream()
			.map(pricesEntity -> buildDomainPrices(Optional.of(pricesEntity)))
			.map(prices -> prices.get())
			.collect(Collectors.toList());

	}

	@Override
	@Transactional
	public Optional<Prices> save(PriceCommand pricesCmd) {
		PricesEntity entityPrices = jpaPriceRepository.save(PriceMapper.mapperToPricesEntity(pricesCmd));
		Optional<PricesEntity> optPricesEntity = Optional.of(entityPrices);
		log.info(
				"Finalize execution operation  PriceRepositoryAdapter::save  by product {idProduct} whose result was {result}",
				pricesCmd.productId(), optPricesEntity.isPresent() ? "success" : "fail");
		return buildDomainPrices(optPricesEntity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		jpaPriceRepository.deleteById(id);
		log.info(
				"Finalize execution operation  PriceRepositoryAdapter::deleteById  by IdRowPrice {id} whose result was {result} success ");

	}

	private Optional<Prices> buildDomainPrices(Optional<PricesEntity> optEntityPrices) {
		return optEntityPrices.map(pricesEntity -> Prices.builder()
			.id(pricesEntity.getId())
			.brand(pricesEntity.getBrandID())
			.productId(pricesEntity.getProductId())
			.priceList(pricesEntity.getPriceList())
			.priority(pricesEntity.getPriority())
			.startDate(pricesEntity.getStartDate())
			.endDate(pricesEntity.getEndDate())
			.price(pricesEntity.getPrice())
			.curr(pricesEntity.getCurr())
			.build());
	}

}
