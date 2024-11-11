
package com.grupobnc.itx.finalprice.application.port.in;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CheckPriceCommandTest {

	@Test
	void shouldBuildRecordConstructor() {
		Long productId = 1L;
		Long brandId = 2L;
		LocalDateTime applicationDate = LocalDateTime.of(2024, 11, 10, 15, 0);

		CheckPriceCommand command = new CheckPriceCommand(productId, brandId, applicationDate);
		assertEquals(productId, command.productId());
		assertEquals(brandId, command.brandId());
		assertEquals(applicationDate, command.applicationDate());

	}

	@Test
	void shouldBuildStaticFactoryMethod() {
		Long productId = 1L;
		Long brandId = 2L;
		LocalDateTime applicationDate = LocalDateTime.of(2024, 11, 10, 15, 0);

		CheckPriceCommand command = CheckPriceCommand.of(productId, brandId, applicationDate);

		assertEquals(productId, command.productId());
		assertEquals(brandId, command.brandId());
		assertEquals(applicationDate, command.applicationDate());
	}

	@Test
	void shouldBuildNullValues() {
		Long productId = null;
		Long brandId = null;
		LocalDateTime applicationDate = null;

		CheckPriceCommand command = CheckPriceCommand.of(productId, brandId, applicationDate);

		assertNull(command.productId());
		assertNull(command.brandId());
		assertNull(command.applicationDate());
	}

}

