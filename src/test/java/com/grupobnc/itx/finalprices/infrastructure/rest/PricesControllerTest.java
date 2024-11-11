package com.grupobnc.itx.finalprices.infrastructure.rest;

import com.grupobnc.itx.finalprices.application.port.in.CheckPriceCommand;
import com.grupobnc.itx.finalprices.application.port.in.PriceCommand;
import com.grupobnc.itx.finalprices.application.port.in.PricePort;
import com.grupobnc.itx.finalprices.application.port.out.PriceCheckedResult;
import com.grupobnc.itx.finalprices.domain.Currency;
import com.grupobnc.itx.finalprices.domain.Prices;
import com.grupobnc.itx.finalprices.dto.in.AplicablePriceReqDTO;
import com.grupobnc.itx.finalprices.dto.in.PriceReqDTO;
import com.grupobnc.itx.finalprices.dto.out.PriceResultDTO;
import com.grupobnc.itx.finalprices.infrastructure.mapper.PriceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class PricesControllerTest {

	@Mock
	private PricePort pricePort;

	@Mock
	private PriceMapper priceMapper;

	@InjectMocks
	private PricesController pricesController;

	private MockMvc mockMvc;

	private Prices prices, prices2;

	PriceReqDTO priceReqDTO;

	@BeforeEach
	void setUp() {
		prices = new Prices(1L, 1L, LocalDateTime.of(2020, 06, 14, 00, 00, 00),
				LocalDateTime.of(2020, 12, 14, 00, 00, 00), 1, 35455L, 1, new BigDecimal(35.50), Currency.EUR);
		prices2 = new Prices(2L, 1L, LocalDateTime.of(2020, 07, 14, 00, 00, 00),
				LocalDateTime.of(2020, 11, 14, 00, 00, 00), 1, 35455L, 1, new BigDecimal(28.77), Currency.EUR);
		mockMvc = MockMvcBuilders.standaloneSetup(pricesController).build();
		priceReqDTO = new PriceReqDTO(35455L, 1L, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, 1,
				new BigDecimal(35.50), Currency.EUR);
	}

	@Test
	@DisplayName("Given a valid PriceReqDTO, When I create or update a price, Then I should return an ApiPriceResponseDTO with OK status")
	void ShouldCreateOrUpdatePrice() throws Exception {
		// Given

		PriceCommand priceCommand = new PriceCommand(35455L, 1L, LocalDateTime.of(2020, 06, 14, 00, 00, 00),
				LocalDateTime.of(2020, 12, 31, 23, 59, 59), 1, 1, new BigDecimal(35.50), Currency.EUR);

		PriceResultDTO priceResultDTO = new PriceResultDTO(1L, 35455L, 1L, LocalDateTime.of(2020, 06, 14, 00, 00, 00),
				LocalDateTime.of(2020, 12, 31, 23, 59, 59), 1, 1, new BigDecimal(35.50), Currency.EUR);

		Mockito.when(pricePort.save(priceCommand)).thenReturn(Optional.of(priceResultDTO));

		// When
		mockMvc
			.perform(post("/api/v1/prices").contentType(MediaType.APPLICATION_JSON).content(asJsonString(priceReqDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());

		// Then
		Mockito.verify(pricePort, Mockito.times(1)).save(priceCommand);
	}

	@Test
	@DisplayName("Given a valid AplicablePriceReqDTO, When I get the final price, Then I should return an ApiPriceResponseDTO with OK status")
	void testGetFinalPrice() throws Exception {
		// Given
		AplicablePriceReqDTO finalPriceReqDTO = new AplicablePriceReqDTO(35455L, 1L, "2020-06-14T00:00:00");

		CheckPriceCommand checkPriceCommand = new CheckPriceCommand(35455L, 1L,
				LocalDateTime.of(2020, 06, 14, 00, 00, 00));
		PriceCheckedResult priceCheckedResult = new PriceCheckedResult(35455L, 1L, 1,
				LocalDateTime.of(2020, 06, 14, 00, 00, 00), LocalDateTime.of(2020, 12, 31, 23, 59, 59),
				new BigDecimal(35.50), Currency.EUR);

		Mockito.when(pricePort.findFinalPricesByApplicationDate(checkPriceCommand))
			.thenReturn(Optional.of(priceCheckedResult));

		// When
		mockMvc
			.perform(post("/api/v1/prices/final").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(finalPriceReqDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());

		// Then
		Mockito.verify(pricePort, Mockito.times(1)).findFinalPricesByApplicationDate(checkPriceCommand);
	}

	@Test
	@DisplayName("Given a valid brand ID and product ID, When I find list price available of product, Then I should return an ApiPriceResponseDTO with OK status")
	void testFindListPriceAvailableOfProduct() throws Exception {
		// Given
		Long brandId = 1L;
		Long productId = 1L;
		List<Prices> pricesList = Arrays.asList(prices, prices2);
		Mockito.when(pricePort.findListPriceAvailableOfProduct(brandId, productId)).thenReturn(pricesList);

		// When
		mockMvc
			.perform(get("/api/v1/prices/brand/{brandId}/product/{productId}", brandId, productId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isNotEmpty());

		// Then
		Mockito.verify(pricePort, Mockito.times(1)).findListPriceAvailableOfProduct(brandId, productId);
	}

	@Test
	@DisplayName("Given a valid ID, When I delete a price by ID, Then I should return an ApiPriceResponseDTO with OK status")
	void shouldDeletePrice() throws Exception {
		// Given
		Long id = 1L;

		// When
		mockMvc.perform(delete("/api/v1/prices/{id}", id).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		// Then
		Mockito.verify(pricePort, Mockito.times(1)).deleteById(id);
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
