package com.grupobnc.itx.checkprices.infrastructure.repository;

import com.grupobnc.itx.checkprices.domain.Currency;
import com.grupobnc.itx.checkprices.infrastructure.entity.PricesEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PriceRepositoryAdapterIntegrationTest {

	@Autowired
	private JpaPriceRepository jpaPriceRepository;

	@Test
	void shouldSave() {
		PricesEntity pricesEntity = new PricesEntity(25L, 1L, 35455L, LocalDateTime.of(2024, 07, 14, 00, 00, 00),
				LocalDateTime.of(2024, 11, 24, 00, 00, 00), 1, 1, new BigDecimal(28.50), Currency.USD);

		PricesEntity resultEntity = jpaPriceRepository.save(pricesEntity);

		assertEquals(35455L, resultEntity.getProductId());
		assertEquals(1L, resultEntity.getBrandID());
		assertEquals(28.50, resultEntity.getPrice().doubleValue());
		assertEquals(Currency.USD, resultEntity.getCurr());
		assertEquals(1, resultEntity.getPriceList());
		assertEquals(1, resultEntity.getPriority());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-14T10:00:00 and product ID 35455 and brand ID 1")
	void shouldFindFinalPricesByApplicationDateReqHr10Date14() {
		// Given a price exists with application date

		// When I search for final prices
		Optional<PricesEntity> resultEntity = jpaPriceRepository
			.findFinalPricesByApplicationDate(LocalDateTime.of(2020, 06, 14, 10, 00, 00), 35455L, 1L);

		// Then I should find this final price
		assertTrue(resultEntity.isPresent());
		assertEquals(35.50, resultEntity.get().getPrice().doubleValue());
		assertEquals(1, resultEntity.get().getPriceList());
		assertEquals(0, resultEntity.get().getPriority());
		assertEquals(35455L, resultEntity.get().getProductId());
		assertEquals(1L, resultEntity.get().getBrandID());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-14T16:00:00 and product ID 35455 and brand ID 1")
	void shouldFindFinalPricesByApplicationDateReqHr16Date14() {
		// Given a price exists with application date

		// When I search for final prices with application date
		Optional<PricesEntity> resultEntity = jpaPriceRepository
			.findFinalPricesByApplicationDate(LocalDateTime.of(2020, 06, 14, 16, 00, 00), 35455L, 1L);

		// Then I should find this final price
		assertTrue(resultEntity.isPresent());
		assertEquals(25.45, resultEntity.get().getPrice().doubleValue());
		assertEquals(2, resultEntity.get().getPriceList());
		assertEquals(1, resultEntity.get().getPriority());
		assertEquals(35455L, resultEntity.get().getProductId());
		assertEquals(1L, resultEntity.get().getBrandID());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-14T21:00:00 and product ID 35455 and brand ID 1")
	void shouldFindFinalPricesByApplicationDateReqHr21Date14() {
		// Given a price exists with application date

		// When I search for final prices with application date
		Optional<PricesEntity> resultEntity = jpaPriceRepository
			.findFinalPricesByApplicationDate(LocalDateTime.of(2020, 06, 14, 21, 00, 00), 35455L, 1L);

		// Then I should find this final price
		assertTrue(resultEntity.isPresent());
		assertEquals(35.50, resultEntity.get().getPrice().doubleValue());
		assertEquals(1, resultEntity.get().getPriceList());
		assertEquals(0, resultEntity.get().getPriority());
		assertEquals(35455L, resultEntity.get().getProductId());
		assertEquals(1L, resultEntity.get().getBrandID());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-15T10:00:00 and product ID 35455 and brand ID 1")
	void shouldFindFinalPricesByApplicationDateReqHr10Date15() {
		// Given a price exists with application date

		// When I search for final prices
		Optional<PricesEntity> resultEntity = jpaPriceRepository
			.findFinalPricesByApplicationDate(LocalDateTime.of(2020, 06, 15, 10, 00, 00), 35455L, 1L);

		// Then I should find this final price
		assertTrue(resultEntity.isPresent());
		assertEquals(30.50, resultEntity.get().getPrice().doubleValue());
		assertEquals(3, resultEntity.get().getPriceList());
		assertEquals(1, resultEntity.get().getPriority());
		assertEquals(35455L, resultEntity.get().getProductId());
		assertEquals(1L, resultEntity.get().getBrandID());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-16T21:00:00 and product ID 35455 and brand ID 1")
	void shouldFindFinalPricesByApplicationDateReqHr21Date16() {
		// Given a price exists with application date

		// When I search for final prices with application date
		Optional<PricesEntity> resultEntity = jpaPriceRepository
			.findFinalPricesByApplicationDate(LocalDateTime.of(2020, 06, 16, 21, 00, 00), 35455L, 1L);

		// Then I should find this final price
		assertTrue(resultEntity.isPresent());
		assertEquals(38.95, resultEntity.get().getPrice().doubleValue());
		assertEquals(4, resultEntity.get().getPriceList());
		assertEquals(1, resultEntity.get().getPriority());
		assertEquals(35455L, resultEntity.get().getProductId());
		assertEquals(1L, resultEntity.get().getBrandID());
	}

	@Test
	void shouldFindListPriceAvailableOfProduct() {
		List<PricesEntity> resultLst = jpaPriceRepository.findListPriceAvailableOfProduct(35455L, 1L);
		assertFalse(resultLst.isEmpty());
		assertEquals(4, resultLst.size());
		assertEquals(1L, resultLst.get(0).getId());
		assertEquals(35455L, resultLst.get(0).getProductId());
		assertEquals(1L, resultLst.get(0).getBrandID());
		assertEquals(35.50, resultLst.get(0).getPrice().doubleValue());
		assertEquals(Currency.EUR, resultLst.get(0).getCurr());
		assertEquals(1, resultLst.get(0).getPriceList());
		assertEquals(0, resultLst.get(0).getPriority());
	}

	@Test
	void shouldDeleteById() {
		jpaPriceRepository.deleteById(25L);
		Optional<PricesEntity> resultEntity = jpaPriceRepository
			.findFinalPricesByApplicationDate(LocalDateTime.of(2024, 07, 15, 00, 00, 00), 35455L, 1L);
		assertFalse(resultEntity.isPresent());
	}

}
