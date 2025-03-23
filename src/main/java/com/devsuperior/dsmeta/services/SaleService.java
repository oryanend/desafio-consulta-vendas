package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> findReports(String minDateStr, String maxDateStr,String name, Pageable pageable) {
		LocalDate maxDate = (maxDateStr == null || maxDateStr.isBlank())? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : convertStringToDate(maxDateStr);

		LocalDate minDate = (minDateStr == null || minDateStr.isBlank())? maxDate.minusYears(1L):convertStringToDate(minDateStr);

		Page<SaleMinDTO> sales = repository.searchReport(name, minDate, maxDate, pageable);

		return sales;
	}

	public List<SaleSummaryDTO> findSummaries(String minDateStr, String maxDateStr) {
		LocalDate maxDate = (maxDateStr == null || maxDateStr.isBlank())? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : convertStringToDate(maxDateStr);

		LocalDate minDate = (minDateStr == null || minDateStr.isBlank())? maxDate.minusYears(1L):convertStringToDate(minDateStr);

		List<SaleSummaryDTO> sales = repository.searchSummaryReport(minDate, maxDate);
		return sales;
	}

	private static LocalDate convertStringToDate(String str) {
		if (str == null || str.isBlank()) {
			throw new IllegalArgumentException("A data fornecida é inválida!");
		}
		return LocalDate.parse(str);
	}

}
