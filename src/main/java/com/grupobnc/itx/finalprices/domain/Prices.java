package com.grupobnc.itx.finalprices.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prices {

	private Long id;

	private Long brand;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private int priceList;

	private Long productId;

	private int priority;

	private BigDecimal price;

	private Currency curr;

}