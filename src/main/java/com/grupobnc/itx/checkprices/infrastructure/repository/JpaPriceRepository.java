package com.grupobnc.itx.checkprices.infrastructure.repository;

import com.grupobnc.itx.checkprices.infrastructure.entity.PricesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaPriceRepository extends JpaRepository<PricesEntity, Long> {

	@Query("Select p FROM PricesEntity p WHERE (p.startDate <= :applicationDate and  p.endDate >=:applicationDate) and p.productId= :productId and p.brandID = :brandID ORDER BY p.priority DESC LIMIT 1")
	Optional<PricesEntity> findFinalPricesByApplicationDate(@Param("applicationDate") LocalDateTime applicationDate,
			@Param("productId") Long productId, @Param("brandID") Long brandID);

	@Query("Select p FROM PricesEntity p WHERE p.productId= :productId and p.brandID = :brandID ")
	List<PricesEntity> findListPriceAvailableOfProduct(@Param("productId") Long productId,
			@Param("brandID") Long brandID);

}
