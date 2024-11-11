package com.grupobnc.itx.finalprices.infrastructure.rest;

import com.grupobnc.itx.finalprices.domain.Prices;
import com.grupobnc.itx.finalprices.dto.in.AplicablePriceReqDTO;
import com.grupobnc.itx.finalprices.dto.in.PriceReqDTO;
import com.grupobnc.itx.finalprices.dto.out.ApiPriceResponseDTO;
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

@RequestMapping("/api/v1/prices")
@Validated
public interface PricesApi {

	@Operation(summary = "Create or update a price", description = "Creates a new price entry or updates an existing one")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Price created or updated successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiPriceResponseDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping
	<T> ResponseEntity<ApiPriceResponseDTO<T>> createOrUpdatePrice(@Valid @RequestBody PriceReqDTO priceReqDTO);

	@Operation(summary = "Get Final Price", description = "Final price retrieved successfully")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Final price retrieved successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiPriceResponseDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@PostMapping("/final")
	<T> ResponseEntity<ApiPriceResponseDTO<T>> getFinalPrice(@Valid @RequestBody AplicablePriceReqDTO finalPriceReqDTO);

	@Operation(summary = "Find list of available prices by product", description = "list of available prices by product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Find list of available prices by product", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiPriceResponseDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@GetMapping("/brand/{brandId}/product/{productId}")
	<T> ResponseEntity<ApiPriceResponseDTO<List<Prices>>> findListPriceAvailableOfProduct(@PathVariable Long brandId,
			@PathVariable Long productId);

	@Operation(summary = "Delete a price", description = "Delete a price")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Delete a price", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiPriceResponseDTO.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
	@DeleteMapping("/{id}")
	<T> ResponseEntity<ApiPriceResponseDTO<T>> deletePrice(@PathVariable Long id);

}
