package com.grupobnc.itx.finalprices.application.service;

import com.grupobnc.itx.finalprices.application.port.in.CheckPriceCommand;
import com.grupobnc.itx.finalprices.application.port.in.PriceCommand;
import com.grupobnc.itx.finalprices.application.port.out.PriceCheckedResult;
import com.grupobnc.itx.finalprices.domain.Currency;
import com.grupobnc.itx.finalprices.domain.Prices;
import com.grupobnc.itx.finalprices.domain.repository.PriceRepository;
import com.grupobnc.itx.finalprices.dto.out.PriceResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DomainPriceServiceTest {

	@Mock
	private PriceRepository priceRepository;

	private DomainPriceService domainPriceService;

	private Prices prices, prices2;

	@BeforeEach
	void setUp() {
		prices = new Prices(1L, 1L, LocalDateTime.of(2020, 06, 14, 00, 00, 00),
				LocalDateTime.of(2020, 12, 14, 00, 00, 00), 1, 35455L, 1, new BigDecimal(35.50), Currency.EUR);
		prices2 = new Prices(2L, 1L, LocalDateTime.of(2020, 07, 14, 00, 00, 00),
				LocalDateTime.of(2020, 11, 14, 00, 00, 00), 1, 35455L, 1, new BigDecimal(28.77), Currency.EUR);
		domainPriceService = new DomainPriceService(priceRepository);
	}

	@Test
	@DisplayName("Given a valid CheckPriceCommand, When I get the applicable price, Then I should return a PriceCheckedResult")
	void shouldGetApplicablePrice() {
		// Given
		CheckPriceCommand checkPriceCommand = new CheckPriceCommand(1L, 1L, LocalDateTime.of(2024, 07, 14, 00, 00, 00));

		Mockito.when(priceRepository.findFinalPricesByApplicationDate(checkPriceCommand))
			.thenReturn(Optional.of(prices));

		// When
		Optional<PriceCheckedResult> resultPriceChecked = domainPriceService
			.findFinalPricesByApplicationDate(checkPriceCommand);

		// Then
		assertTrue(resultPriceChecked.isPresent());
		assertEquals(prices.getPriceList(), resultPriceChecked.get().rateToApply());
		assertEquals(prices.getPrice(), resultPriceChecked.get().finalPrice());
		assertEquals(prices.getCurr(), resultPriceChecked.get().cur());
		assertEquals(prices.getStartDate(), resultPriceChecked.get().applicationDateStart());
		assertEquals(prices.getProductId(), resultPriceChecked.get().productId());

	}

	@Test
	@DisplayName("Given a valid PriceCommand, When I save a price, Then I should return a PriceResultDTO")
	void shouldSaveNewPriceOnList() {
		// Given
		PriceCommand priceCommand = new PriceCommand(35455L, 1L, LocalDateTime.of(2020, 06, 14, 00, 00, 00),
				LocalDateTime.of(2020, 06, 14, 00, 00, 00), 1, 1, new BigDecimal(35.50), Currency.EUR);
		;

		Mockito.when(priceRepository.save(priceCommand)).thenReturn(Optional.of(prices));

		// When
		Optional<PriceResultDTO> resultPrice = domainPriceService.save(priceCommand);

		// Then
		assertTrue(resultPrice.isPresent());
		assertEquals(priceCommand.brandId(), resultPrice.get().brandId());
		assertEquals(priceCommand.priceList(), resultPrice.get().priceList());
		assertEquals(priceCommand.priority(), resultPrice.get().priority());
		assertEquals(priceCommand.price().doubleValue(), resultPrice.get().price().doubleValue());
		assertEquals(priceCommand.productId(), resultPrice.get().productId());

	}

	@Test
	@DisplayName("Given a valid brand ID and product ID, When I find list price available of product, Then I should return a list of Prices")
	void shouldFindListPriceAvailableOfProduct() {
		// Given
		Long brandId = 1L;
		Long productId = 1L;
		List<Prices> pricesList = Arrays.asList(prices, prices2);
		Mockito.when(priceRepository.findListPriceAvailableOfProduct(brandId, productId)).thenReturn(pricesList);

		// When
		List<Prices> result = domainPriceService.findListPriceAvailableOfProduct(brandId, productId);

		// Then
		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
	}

	@Test
	@DisplayName("Given a valid ID, When I delete a price by ID, Then it should call the repository's deleteById method")
	void shouldDeleteById() {
		// Given
		Long id = 1L;

		// When
		domainPriceService.deleteById(id);

		// Then
		Mockito.verify(priceRepository, Mockito.times(1)).deleteById(id);
	}

}
