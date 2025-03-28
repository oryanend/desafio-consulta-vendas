package com.devsuperior.dsmeta;

import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;

@SpringBootApplication
public class DsmetaApplication{

	@Autowired
	SaleRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DsmetaApplication.class, args);
	}

}
