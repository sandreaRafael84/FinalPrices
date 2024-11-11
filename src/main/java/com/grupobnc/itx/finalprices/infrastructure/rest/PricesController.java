package com.grupobnc.itx.finalprices.infrastructure.rest;

import com.grupobnc.itx.finalprices.application.port.in.CheckPriceCommand;
import com.grupobnc.itx.finalprices.application.port.in.PriceCommand;
import com.grupobnc.itx.finalprices.application.port.in.PricePort;
import com.grupobnc.itx.finalprices.common.RestApi;
import com.grupobnc.itx.finalprices.domain.Prices;
import com.grupobnc.itx.finalprices.dto.in.AplicablePriceReqDTO;
import com.grupobnc.itx.finalprices.dto.in.PriceReqDTO;
import com.grupobnc.itx.finalprices.dto.out.ApiError;
import com.grupobnc.itx.finalprices.dto.out.ApiMetadata;
import com.grupobnc.itx.finalprices.dto.out.ApiPriceResponseDTO;
import com.grupobnc.itx.finalprices.infrastructure.mapper.PriceMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import static com.grupobnc.itx.finalprices.util.Utility.BUSINESS_ERROR_CODE;
import static com.grupobnc.itx.finalprices.util.Utility.BUSINESS_ERROR_MISSING;
import static com.grupobnc.itx.finalprices.util.Utility.OP_CREATE_UPDATE_PRICE;
import static com.grupobnc.itx.finalprices.util.Utility.OP_DELETE_PRICE;
import static com.grupobnc.itx.finalprices.util.Utility.OP_FIND_FINAL_PRICE;
import static com.grupobnc.itx.finalprices.util.Utility.OP_FIND_LIST_PRICE_AVAILABLE_PRODUCT;

@RestApi
@Slf4j
public class PricesController implements PricesApi {

	private final PricePort pricePort;

	public PricesController(PricePort pricePort) {
		this.pricePort = pricePort;
	}

	@Override
	public <T> ResponseEntity<ApiPriceResponseDTO<T>> createOrUpdatePrice(@Valid @RequestBody PriceReqDTO priceReqDTO) {
		log.info("start operation {op} by product {idProduct}", OP_CREATE_UPDATE_PRICE, priceReqDTO.productId());

		Optional<PriceCommand> optPriceCmd = PriceMapper.mapperToPriceCmd(priceReqDTO);

		if (optPriceCmd.isPresent()) {
			return new ResponseEntity<>(
					PriceMapper.BuildApiResponse(OP_CREATE_UPDATE_PRICE, pricePort.save(optPriceCmd.get())),
					HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(ApiPriceResponseDTO.fromFailure(ApiMetadata.of(OP_CREATE_UPDATE_PRICE),
					ApiError.of(BUSINESS_ERROR_CODE, BUSINESS_ERROR_MISSING)), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public <T> ResponseEntity<ApiPriceResponseDTO<T>> getFinalPrice(
			@Valid @RequestBody AplicablePriceReqDTO finalPriceReqDTO) {
		log.info("start operation {op} by date {applDate}", OP_FIND_FINAL_PRICE, finalPriceReqDTO.applicationDate());

		Optional<CheckPriceCommand> optCheckPriceCmd = PriceMapper.mapperToCheckPriceCmd(finalPriceReqDTO);

		if (optCheckPriceCmd.isPresent()) {
			return new ResponseEntity<>(PriceMapper.BuildApiResponse(OP_FIND_FINAL_PRICE,
					pricePort.findFinalPricesByApplicationDate(optCheckPriceCmd.get())), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(ApiPriceResponseDTO.fromFailure(ApiMetadata.of(OP_FIND_FINAL_PRICE),
					ApiError.of(BUSINESS_ERROR_CODE, BUSINESS_ERROR_MISSING)), HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public <T> ResponseEntity<ApiPriceResponseDTO<List<Prices>>> findListPriceAvailableOfProduct(
			@PathVariable Long brandId, @PathVariable Long productId) {
		log.info(" start operation {op}  by product {id} ", OP_FIND_LIST_PRICE_AVAILABLE_PRODUCT, productId);

		return new ResponseEntity<>(
				ApiPriceResponseDTO.fromSuccess(ApiMetadata.of(OP_FIND_LIST_PRICE_AVAILABLE_PRODUCT),
						pricePort.findListPriceAvailableOfProduct(brandId, productId)),
				HttpStatus.OK);
	}

	@Override
	public <T> ResponseEntity<ApiPriceResponseDTO<T>> deletePrice(@PathVariable Long id) {
		log.info("start operation {op} by product {id}", OP_DELETE_PRICE, id);
		pricePort.deleteById(id);
		return new ResponseEntity<>(
				PriceMapper.BuildApiResponse(OP_DELETE_PRICE, Optional.of("Operation Successfully")), HttpStatus.OK);
	}

}