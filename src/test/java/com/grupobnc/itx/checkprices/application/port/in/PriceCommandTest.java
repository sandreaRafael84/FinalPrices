package com.grupobnc.itx.checkprices.application.port.in;

import com.grupobnc.itx.checkprices.domain.Currency;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PriceCommandTest {

	private Validator validator;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void shouldBuildRecordConstructor() {
		Long productId = 1L;
		Long brandId = 2L;
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);
		int priceList = 1;
		int priority = 1;
		BigDecimal price = new BigDecimal("100.00");

		PriceCommand command = new PriceCommand(productId, brandId, startDate, endDate, priceList, priority, price,
				Currency.EUR);

		assertEquals(productId, command.productId());
		assertEquals(brandId, command.brandId());
		assertEquals(startDate, command.startDate());
		assertEquals(endDate, command.endDate());
		assertEquals(priceList, command.priceList());
		assertEquals(priority, command.priority());
		assertEquals(price, command.price());
		assertEquals(Currency.EUR, command.curr());
	}

	@Test
	void shouldBuildStaticFactoryMethod() {
		Long productId = 1L;
		Long brandId = 2L;
		LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);
		int priceList = 1;
		int priority = 1;
		BigDecimal price = new BigDecimal("100.00");

		PriceCommand command = PriceCommand.of(productId, brandId, startDate, endDate, priceList, priority, price,
				Currency.EUR);

		assertEquals(productId, command.productId());
		assertEquals(brandId, command.brandId());
		assertEquals(startDate, command.startDate());
		assertEquals(endDate, command.endDate());
		assertEquals(priceList, command.priceList());
		assertEquals(priority, command.priority());
		assertEquals(price, command.price());
		assertEquals(Currency.EUR, command.curr());
	}

	@Test
	void ShouldValidationAnnotations() {
		Long productId = null;
		Long brandId = 1L;
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;
		int priceList = 1;
		int priority = 1;
		BigDecimal price = new BigDecimal("-100.00");

		PriceCommand command = PriceCommand.of(productId, brandId, startDate, endDate, priceList, priority, price,
				Currency.EUR);

		Set<ConstraintViolation<PriceCommand>> violations = validator.validate(command);

		assertFalse(violations.isEmpty());
		assertEquals(4, violations.size());

		for (ConstraintViolation<PriceCommand> violation : violations) {
			String propertyPath = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			switch (propertyPath) {
				case "productId":
					assertEquals("productId cannot be null", message);
					break;
				case "startDate":
					assertEquals("start Date cannot be null", message);
					break;
				case "endDate":
					assertEquals("End Date cannot be null", message);
					break;
				case "price":
					assertEquals("Price must be positive", message);
					break;
				default:
					String fail = "Unexpected violation: " + propertyPath + " - " + message;
			}
		}
	}

}
