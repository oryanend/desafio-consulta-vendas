package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = """
    SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(s.id, s.amount, s.date, s.seller.name) 
    FROM Sale s 
    JOIN s.seller sl 
    WHERE UPPER(sl.name) LIKE UPPER(CONCAT('%', :name, '%')) 
    AND s.date BETWEEN :minDate AND :maxDate
    """, countQuery = """
    SELECT COUNT(s) FROM Sale s 
    JOIN s.seller sl 
    WHERE UPPER(sl.name) LIKE UPPER(CONCAT('%', :name, '%')) 
    AND s.date BETWEEN :minDate AND :maxDate
    """
    )
    Page<SaleMinDTO> searchReport(String name, LocalDate minDate, LocalDate maxDate, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(s.seller.name, SUM(s.amount)) FROM Sale s WHERE s.date BETWEEN :minDate AND :maxDate GROUP BY s.seller.id, s.seller.name ORDER BY s.seller.name")
    List<SaleSummaryDTO> searchSummaryReport(LocalDate minDate, LocalDate maxDate);

}
