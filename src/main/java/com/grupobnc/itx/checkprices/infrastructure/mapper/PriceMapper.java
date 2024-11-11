package com.grupobnc.itx.checkprices.infrastructure.mapper;

import com.grupobnc.itx.checkprices.application.port.in.CheckPriceCommand;
import com.grupobnc.itx.checkprices.application.port.in.PriceCommand;
import com.grupobnc.itx.checkprices.dto.in.AplicablePriceReqDTO;
import com.grupobnc.itx.checkprices.dto.in.PriceReqDTO;
import com.grupobnc.itx.checkprices.dto.out.ApiError;
import com.grupobnc.itx.checkprices.dto.out.ApiMetadata;
import com.grupobnc.itx.checkprices.dto.out.ApiPriceResponseDTO;
import com.grupobnc.itx.checkprices.infrastructure.entity.PricesEntity;
import com.grupobnc.itx.checkprices.util.Utility;

import java.util.Optional;

import static com.grupobnc.itx.checkprices.util.Utility.BUSINESS_ERROR_CODE;
import static com.grupobnc.itx.checkprices.util.Utility.BUSINESS_ERROR_NOT_FOUND;

public interface PriceMapper {

	static PricesEntity mapperToPricesEntity(PriceCommand priceCommand) {

		PricesEntity pricesEntity = PricesEntity.builder()
			.productId(priceCommand.productId())
			.brandID(priceCommand.brandId())
			.price(priceCommand.price())
			.priceList(priceCommand.priceList())
			.startDate(priceCommand.startDate())
			.endDate(priceCommand.endDate())
			.priority(priceCommand.priority())
			.curr(priceCommand.curr())
			.build();
		return pricesEntity;
	}

	static Optional<CheckPriceCommand> mapperToCheckPriceCmd(AplicablePriceReqDTO aplicablePriceReqDTO) {
		return Optional.of(CheckPriceCommand.of(aplicablePriceReqDTO.productId(), aplicablePriceReqDTO.brandId(),
				Utility.parseDate(aplicablePriceReqDTO.applicationDate())));

	}

	static Optional<PriceCommand> mapperToPriceCmd(PriceReqDTO priceReqDTO) {

		return Optional.of(PriceCommand.of(priceReqDTO.productId(), priceReqDTO.brandId(),
				Utility.parseDate(priceReqDTO.startDate()), Utility.parseDate(priceReqDTO.endDate()),
				priceReqDTO.priceList(), priceReqDTO.priority(), priceReqDTO.price(), priceReqDTO.curr()));
	}

	static <T> ApiPriceResponseDTO BuildApiResponse(String operation, Optional<T> optResult) {
		if (optResult.isPresent()) {
			return ApiPriceResponseDTO.fromSuccess(ApiMetadata.of(operation), optResult.get());
		}
		else {
			return ApiPriceResponseDTO.fromFailure(ApiMetadata.of(operation),
					ApiError.of(BUSINESS_ERROR_CODE, BUSINESS_ERROR_NOT_FOUND));
		}
	}

}
