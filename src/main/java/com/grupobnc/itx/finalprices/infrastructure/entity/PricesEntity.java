package com.grupobnc.itx.finalprices.infrastructure.entity;

import com.grupobnc.itx.finalprices.domain.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "Prices")
public class PricesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "BRAND_ID", nullable = false)
	private Long brandID;

	@Column(name = "PRODUCT_ID", nullable = false)
	private Long productId;

	@Column(name = "START_DATE", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "END_DATE", nullable = false)
	private LocalDateTime endDate;

	@Column(name = "PRICE_LIST", nullable = false)
	private int priceList;

	@Column(name = "PRIORITY", nullable = false)
	private int priority;

	@Column(name = "PRICE", nullable = false)
	private BigDecimal price;

	@Enumerated(EnumType.STRING)
	@Column(name = "CURR", nullable = false)
	private Currency curr;

	public PricesEntity() {
	}

	public PricesEntity(Long id, Long brandID, Long productId, LocalDateTime startDate, LocalDateTime endDate,
			int priceList, int priority, BigDecimal price, Currency curr) {
		this.id = id;
		this.brandID = brandID;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceList = priceList;
		this.productId = productId;
		this.priority = priority;
		this.price = price;
		this.curr = curr;
	}

}