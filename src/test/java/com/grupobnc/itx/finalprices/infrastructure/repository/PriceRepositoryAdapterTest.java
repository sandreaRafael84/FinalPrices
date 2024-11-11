package com.grupobnc.itx.finalprices.infrastructure.repository;

import com.grupobnc.itx.finalprices.application.port.in.CheckPriceCommand;
import com.grupobnc.itx.finalprices.application.port.in.PriceCommand;
import com.grupobnc.itx.finalprices.domain.Currency;
import com.grupobnc.itx.finalprices.domain.Prices;
import com.grupobnc.itx.finalprices.infrastructure.entity.PricesEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceRepositoryAdapterTest {

	@Mock
	private JpaPriceRepository jpaPriceRepository;

	@InjectMocks
	private PriceRepositoryAdapter priceRepositoryAdapter;

	private CheckPriceCommand checkPriceCommand;

	private PriceCommand priceCommand;

	private PricesEntity pricesEntity, pricesEntity2;

	@BeforeEach
	void setUp() {
		checkPriceCommand = new CheckPriceCommand(35455L, 1L, LocalDateTime.of(2020, 06, 14, 00, 00, 00));
		priceCommand = new PriceCommand(35455L, 1L, LocalDateTime.of(2020, 06, 14, 00, 00, 00),
				LocalDateTime.of(2020, 06, 14, 00, 00, 00), 1, 1, new BigDecimal(35.50), Currency.EUR);
		pricesEntity = new PricesEntity(1L, 35455L, 1L, LocalDateTime.of(2020, 06, 14, 00, 00, 00),
				LocalDateTime.of(2020, 06, 14, 00, 00, 00), 1, 1, new BigDecimal(35.50), Currency.EUR);
		pricesEntity2 = new PricesEntity(2L, 35455L, 1L, LocalDateTime.of(2020, 06, 14, 15, 00, 00),
				LocalDateTime.of(2020, 06, 14, 18, 30, 00), 1, 1, new BigDecimal(25.45), Currency.EUR);

	}

	@Test
	void shouldFindFinalPricesByApplicationDate() {
		when(jpaPriceRepository.findFinalPricesByApplicationDate(checkPriceCommand.applicationDate(),
				checkPriceCommand.productId(), checkPriceCommand.brandId()))
			.thenReturn(Optional.of(pricesEntity));

		Optional<Prices> finalPrice = priceRepositoryAdapter.findFinalPricesByApplicationDate(checkPriceCommand);
		assertTrue(finalPrice.isPresent());
		assertEquals(pricesEntity.getId(), finalPrice.get().getId());
		assertEquals(pricesEntity.getBrandID(), finalPrice.get().getBrand());
		assertEquals(pricesEntity.getPrice(), finalPrice.get().getPrice());
		assertEquals(pricesEntity.getCurr(), finalPrice.get().getCurr());
		assertEquals(pricesEntity.getPriceList(), finalPrice.get().getPriceList());
		assertEquals(pricesEntity.getStartDate(), finalPrice.get().getStartDate());
		assertEquals(pricesEntity.getEndDate(), finalPrice.get().getEndDate());
		assertEquals(pricesEntity.getPriority(), finalPrice.get().getPriority());

	}

	@Test
	void shouldFindListPriceAvailableOfProductByIdProductAndBrandId() {
		when(jpaPriceRepository.findListPriceAvailableOfProduct(priceCommand.productId(), priceCommand.brandId()))
			.thenReturn(List.of(pricesEntity, pricesEntity2));

		List<Prices> resultLst = priceRepositoryAdapter.findListPriceAvailableOfProduct(priceCommand.brandId(),
				priceCommand.productId());
		assertFalse(resultLst.isEmpty());
		assertEquals(2, resultLst.size());
		assertEquals(pricesEntity.getId(), resultLst.get(0).getId());
		assertEquals(pricesEntity.getId(), resultLst.get(0).getId());
		assertEquals(pricesEntity.getBrandID(), resultLst.get(0).getBrand());
		assertEquals(pricesEntity.getPrice(), resultLst.get(0).getPrice());
		assertEquals(pricesEntity.getCurr(), resultLst.get(0).getCurr());
		assertEquals(pricesEntity.getPriceList(), resultLst.get(0).getPriceList());
		assertEquals(pricesEntity.getStartDate(), resultLst.get(0).getStartDate());
		assertEquals(pricesEntity.getEndDate(), resultLst.get(0).getEndDate());
		assertEquals(pricesEntity.getPriority(), resultLst.get(0).getPriority());
	}

	@Test
	void ShouldSavePriceEntity() {
		when(jpaPriceRepository.save(any())).thenReturn(pricesEntity);

		Optional<Prices> result = priceRepositoryAdapter.save(priceCommand);
		assertTrue(result.isPresent());
		assertEquals(pricesEntity.getId(), result.get().getId());
		assertEquals(pricesEntity.getBrandID(), result.get().getBrand());
		assertEquals(pricesEntity.getPrice(), result.get().getPrice());
		assertEquals(pricesEntity.getCurr(), result.get().getCurr());
	}

	@Test
	void ShoulDeletePriceEntityById() {
		Long id = 1L;
		priceRepositoryAdapter.deleteById(id);
		// Verify that the deleteById method was called on the JPAPriceRepository
		verify(jpaPriceRepository, times(1)).deleteById(id);
	}

}

