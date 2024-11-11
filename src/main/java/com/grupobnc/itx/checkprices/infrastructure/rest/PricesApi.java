package com.grupobnc.itx.checkprices.infrastructure.rest;

import com.grupobnc.itx.checkprices.domain.Prices;
import com.grupobnc.itx.checkprices.dto.in.AplicablePriceReqDTO;
import com.grupobnc.itx.checkprices.dto.in.PriceReqDTO;
import com.grupobnc.itx.checkprices.dto.out.ApiPriceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@Api(value = "Prices API", tags = "Prices")
@RequestMapping("/api/v1/prices")
@Validated
public interface PricesApi {

	@Operation(summary = "Create or update a price", description = "Creates a new price entry or updates an existing one")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Price created or updated successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiPriceResponseDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping
	<T> ResponseEntity<ApiPriceResponseDTO<T>> createOrUpdatePrice(@Valid @RequestBody PriceReqDTO priceReqDTO);

	/*@ApiOperation(value = "Get final price", response = ApiPriceResponseDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Final price retrieved successfully"),
			@ApiResponse(code = 400, message = "Invalid input"),
			@ApiResponse(code = 500, message = "Internal server error") })*/
	@PostMapping("/final")
	<T> ResponseEntity<ApiPriceResponseDTO<T>> getFinalPrice(@Valid @RequestBody AplicablePriceReqDTO finalPriceReqDTO);

	/*@ApiOperation(value = "Find list of available prices by product", response = ApiPriceResponseDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Prices retrieved successfully"),
			@ApiResponse(code = 400, message = "Invalid input"), @ApiResponse(code = 404, message = "Prices not found"),
			@ApiResponse(code = 500, message = "Internal server error") })*/
	@GetMapping("/brand/{brandId}/product/{productId}")
	<T> ResponseEntity<ApiPriceResponseDTO<List<Prices>>> findListPriceAvailableOfProduct(@PathVariable Long brandId,
			@PathVariable Long productId);

	/*@ApiOperation(value = "Delete a price", response = ApiPriceResponseDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Price deleted successfully"),
			@ApiResponse(code = 400, message = "Invalid input"), @ApiResponse(code = 404, message = "Price not found"),
			@ApiResponse(code = 500, message = "Internal server error") })*/
	@DeleteMapping("/{id}")
	<T> ResponseEntity<ApiPriceResponseDTO<T>> deletePrice(@PathVariable Long id);

}
