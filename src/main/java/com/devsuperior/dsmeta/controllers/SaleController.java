package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<?> getReport(
			@RequestParam(name = "name", defaultValue = "")String name,
			@RequestParam(name = "minDate", defaultValue = "")String minDateStr,
			@RequestParam(name = "maxDate", defaultValue = "")String maxDateStr,
			Pageable pageable
	)
	{
		Page<SaleMinDTO> dto = service.findReports(minDateStr, maxDateStr, name, pageable);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<?> getSummary(
	@RequestParam(name = "minDate", defaultValue = "")String minDateStr, @RequestParam(name = "maxDate", defaultValue = "")String maxDateStr
	) {
		List<SaleSummaryDTO> dto = service.findSummaries(minDateStr, maxDateStr);
		return ResponseEntity.ok().body(dto);
	}
}
