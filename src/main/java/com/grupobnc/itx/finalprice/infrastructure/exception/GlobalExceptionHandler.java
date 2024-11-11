package com.grupobnc.itx.finalprice.infrastructure.exception;

import com.grupobnc.itx.finalprice.dto.out.ApiError;
import com.grupobnc.itx.finalprice.dto.out.ApiMetadata;
import com.grupobnc.itx.finalprice.dto.out.ApiPriceResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.grupobnc.itx.finalprice.util.Utility.BUSINESS_ERROR_CODE;
import static com.grupobnc.itx.finalprice.util.Utility.BUSINESS_ERROR_DATE;
import static com.grupobnc.itx.finalprice.util.Utility.BUSINESS_ERROR_MISSING;
import static com.grupobnc.itx.finalprice.util.Utility.TECHNICAL_ERROR_CODE;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public <T> ResponseEntity<ApiPriceResponseDTO<T>> handleInvalidConverterRequest(
			HttpMessageNotReadableException ex) {
		return new ResponseEntity<>(ApiPriceResponseDTO.fromFailure(ApiMetadata.of("HttpMessageConverter"),
				ApiError.of(BUSINESS_ERROR_CODE, BUSINESS_ERROR_MISSING)), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public <T> ResponseEntity<ApiPriceResponseDTO<T>> handleInvalidRequest(MethodArgumentNotValidException ex) {
		List<String> lstError = new ArrayList<>();

		ex.getBindingResult().getAllErrors().forEach((err -> lstError.add(".- ".concat(err.getDefaultMessage()))));

		return new ResponseEntity<>(
				ApiPriceResponseDTO.fromFailure(ApiMetadata.of("MethodArgumentNotValid"),
						ApiError.of(BUSINESS_ERROR_CODE, BUSINESS_ERROR_MISSING.concat(lstError.toString()))),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DateTimeParseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public <T> ResponseEntity<ApiPriceResponseDTO<T>> handleParseDateException(DateTimeParseException ex) {
		return new ResponseEntity<>(ApiPriceResponseDTO.fromFailure(ApiMetadata.of("DateTimeParse"),
				ApiError.of(BUSINESS_ERROR_CODE, BUSINESS_ERROR_DATE)), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public <T> ResponseEntity<ApiPriceResponseDTO<T>> handleCommonException(Exception ex) {
		return new ResponseEntity<>(ApiPriceResponseDTO.fromFailure(ApiMetadata.of("UnexpectedCondition"),
				ApiError.of(TECHNICAL_ERROR_CODE, ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
