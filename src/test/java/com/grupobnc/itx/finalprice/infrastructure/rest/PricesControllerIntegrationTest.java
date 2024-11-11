package com.grupobnc.itx.finalprice.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupobnc.itx.finalprice.application.port.in.PricePort;
import com.grupobnc.itx.finalprice.domain.Currency;
import com.grupobnc.itx.finalprice.dto.in.AplicablePriceReqDTO;
import com.grupobnc.itx.finalprice.dto.in.PriceReqDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.grupobnc.itx.finalprice.util.Utility.BUSINESS_ERROR_CODE;
import static com.grupobnc.itx.finalprice.util.Utility.BUSINESS_ERROR_DATE;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PricesControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PricePort pricePort;

	@Test
	@DisplayName("When creating or updating a price, Then it should return OK status")
	void shouldCreateOrUpdatePrice() throws Exception {
		// Given
		PriceReqDTO priceReqDTO = new PriceReqDTO(35455L, 1L, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 1, 1,
				new BigDecimal(35.50), Currency.EUR);

		// When
		mockMvc
			.perform(post("/api/v1/prices").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(priceReqDTO)))
			// Then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.metadata.specVersion").value("1.0"))
			.andExpect(jsonPath("$.metadata.operation").value("createOrUpdatePrice"))
			.andExpect(jsonPath("$.data.id").value(25))
			.andExpect(jsonPath("$.data.productId").value(35455))
			.andExpect(jsonPath("$.data.brandId").value(1))
			.andExpect(jsonPath("$.data.startDate").value("2020-06-14T00:00:00"))
			.andExpect(jsonPath("$.data.endDate").value("2020-12-31T23:59:59"))
			.andExpect(jsonPath("$.data.priceList").value(1))
			.andExpect(jsonPath("$.data.priority").value(1))
			.andExpect(jsonPath("$.data.price").value(35.5))
			.andExpect(jsonPath("$.data.curr").value("EUR"))
			.andExpect(jsonPath("$.error").isEmpty());
	}

	@Test
	@DisplayName("Given an invalid PriceReqDTO, When creating or updating a price, Then it should return BAD REQUEST status")
	void ShouldObtainBadRequestDuetoInvalidRequest() throws Exception {
		// Given
		PriceReqDTO priceReqDTO = new PriceReqDTO(5455L, 1L, "2020-06-14 0:00:00", "2020-12-31T23:59:59", 1, 1,
				new BigDecimal(35.50), Currency.EUR);

		// When
		mockMvc
			.perform(post("/api/v1/prices").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(priceReqDTO)))
			// Then
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.error.code").value(BUSINESS_ERROR_CODE))
			.andExpect(jsonPath("$.error.message").value(BUSINESS_ERROR_DATE));
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-14T10:00:00 and product ID 35455 and brand ID 1")
	void shouldFindFinalPricesByApplicationDateReqHr10Date14() throws Exception {
		// Given a price exists with application date
		AplicablePriceReqDTO finalPriceReqDTO = new AplicablePriceReqDTO(35455L, 1L, "2020-06-14T00:00:00");

		// When
		mockMvc
			.perform(post("/api/v1/prices/final").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(finalPriceReqDTO)))
			// Then I should get this final price
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.metadata.specVersion").value("1.0"))
			.andExpect(jsonPath("$.metadata.operation").value("findFinalPrice"))
			.andExpect(jsonPath("$.data.productId").value(35455))
			.andExpect(jsonPath("$.data.supplyChainId").value(1))
			.andExpect(jsonPath("$.data.applicationDateStart").value("2020-06-14T00:00:00"))
			.andExpect(jsonPath("$.data.applicationDateEnd").value("2020-12-31T23:59:59"))
			.andExpect(jsonPath("$.data.rateToApply").value(1))
			.andExpect(jsonPath("$.data.finalPrice").value(35.5))
			.andExpect(jsonPath("$.data.cur").value("EUR"))
			.andExpect(jsonPath("$.error").isEmpty());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-14T16:00:00 and product ID 35455 and brand ID 1")
	void shouldFindFinalPricesByApplicationDateReqHr16Date14() throws Exception {
		// Given a price exists with application date
		AplicablePriceReqDTO finalPriceReqDTO = new AplicablePriceReqDTO(35455L, 1L, "2020-06-14T16:00:00");
		;

		// When
		mockMvc
			.perform(post("/api/v1/prices/final").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(finalPriceReqDTO)))
			// Then I should get this final price
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.metadata.specVersion").value("1.0"))
			.andExpect(jsonPath("$.metadata.operation").value("findFinalPrice"))
			.andExpect(jsonPath("$.data.productId").value(35455))
			.andExpect(jsonPath("$.data.supplyChainId").value(1))
			.andExpect(jsonPath("$.data.applicationDateStart").value("2020-06-14T15:00:00"))
			.andExpect(jsonPath("$.data.applicationDateEnd").value("2020-06-14T18:30:00"))
			.andExpect(jsonPath("$.data.rateToApply").value(2))
			.andExpect(jsonPath("$.data.finalPrice").value(25.45))
			.andExpect(jsonPath("$.data.cur").value("EUR"))
			.andExpect(jsonPath("$.error").isEmpty());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-14T21:00:00 and product ID 35455 and brand ID 1")
	void shouldFindFinalPricesByApplicationDateReqHr21Date14() throws Exception {
		// Given a price exists with application date
		AplicablePriceReqDTO finalPriceReqDTO = new AplicablePriceReqDTO(35455L, 1L, "2020-06-14T16:00:00");
		;

		// When
		mockMvc
			.perform(post("/api/v1/prices/final").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(finalPriceReqDTO)))
			// Then I should get this final price
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.metadata.specVersion").value("1.0"))
			.andExpect(jsonPath("$.metadata.operation").value("findFinalPrice"))
			.andExpect(jsonPath("$.data.productId").value(35455))
			.andExpect(jsonPath("$.data.supplyChainId").value(1))
			.andExpect(jsonPath("$.data.applicationDateStart").value("2020-06-14T15:00:00"))
			.andExpect(jsonPath("$.data.applicationDateEnd").value("2020-06-14T18:30:00"))
			.andExpect(jsonPath("$.data.rateToApply").value(2))
			.andExpect(jsonPath("$.data.finalPrice").value(25.45))
			.andExpect(jsonPath("$.data.cur").value("EUR"))
			.andExpect(jsonPath("$.error").isEmpty());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-15T10:00:00 and product ID 35455 and brand ID 1ww")
	void shouldFindFinalPricesByApplicationDateReqHr10Date15() throws Exception {
		// Given a price exists with application date
		AplicablePriceReqDTO finalPriceReqDTO = new AplicablePriceReqDTO(35455L, 1L, "2020-06-15T10:00:00");
		;

		// When
		mockMvc
			.perform(post("/api/v1/prices/final").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(finalPriceReqDTO)))
			// Then I should get this final price
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.metadata.specVersion").value("1.0"))
			.andExpect(jsonPath("$.metadata.operation").value("findFinalPrice"))
			.andExpect(jsonPath("$.data.productId").value(35455))
			.andExpect(jsonPath("$.data.supplyChainId").value(1))
			.andExpect(jsonPath("$.data.applicationDateStart").value("2020-06-15T00:00:00"))
			.andExpect(jsonPath("$.data.applicationDateEnd").value("2020-06-15T11:00:00"))
			.andExpect(jsonPath("$.data.rateToApply").value(3))
			.andExpect(jsonPath("$.data.finalPrice").value(30.50))
			.andExpect(jsonPath("$.data.cur").value("EUR"))
			.andExpect(jsonPath("$.error").isEmpty());
	}

	@Test
	@DisplayName("When I search for final prices with application date 2020-06-15T10:00:00 and product ID 35455 and brand ID 1ww")
	void shouldFindFinalPricesByApplicationDateReqHr21Date16() throws Exception {
		// Given a price exists with application date
		AplicablePriceReqDTO finalPriceReqDTO = new AplicablePriceReqDTO(35455L, 1L, "2020-06-15T10:00:00");
		;

		// When
		mockMvc
			.perform(post("/api/v1/prices/final").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(finalPriceReqDTO)))
			// Then I should get this final price
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.metadata.specVersion").value("1.0"))
			.andExpect(jsonPath("$.metadata.operation").value("findFinalPrice"))
			.andExpect(jsonPath("$.data.productId").value(35455))
			.andExpect(jsonPath("$.data.supplyChainId").value(1))
			.andExpect(jsonPath("$.data.applicationDateStart").value("2020-06-15T00:00:00"))
			.andExpect(jsonPath("$.data.applicationDateEnd").value("2020-06-15T11:00:00"))
			.andExpect(jsonPath("$.data.rateToApply").value(3))
			.andExpect(jsonPath("$.data.finalPrice").value(30.50))
			.andExpect(jsonPath("$.data.cur").value("EUR"))
			.andExpect(jsonPath("$.error").isEmpty());
	}

	@Test
	@DisplayName("Given an invalid ApplicablePriceReqDTO, When getting final price, Then it should return BAD REQUEST status")
	void testGetFinalPriceInvalid() throws Exception {
		// Given
		AplicablePriceReqDTO finalPriceReqDTO = new AplicablePriceReqDTO(35455L, 1L, "2020-06-14 00:00:00");

		// When
		mockMvc
			.perform(post("/api/v1/prices/final").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(finalPriceReqDTO)))
			.andExpect(status().isBadRequest())
			// Then
			.andExpect(jsonPath("$.error.code").value(BUSINESS_ERROR_CODE))
			.andExpect(jsonPath("$.error.message").value(BUSINESS_ERROR_DATE));
	}

	@Test
	@DisplayName("When finding list price available of product, Then it should return OK status")
	void testFindListPriceAvailableOfProduct() throws Exception {
		// Given
		Long brandId = 1L;
		Long productId = 1L;

		// When
		mockMvc
			.perform(get("/api/v1/prices/brand/{brandId}/product/{productId}", brandId, productId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

	}

	@Test
	@DisplayName("When deleting a price, Then it should return OK status")
	void ShouldDeletePrice() throws Exception {
		// Given
		Long id = 5L;

		// When
		mockMvc.perform(delete("/api/v1/prices/{id}", id).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value("true"));
	}

}
