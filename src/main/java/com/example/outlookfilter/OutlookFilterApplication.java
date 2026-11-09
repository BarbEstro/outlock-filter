package com.example.outlookfilter;

import com.example.outlookfilter.service.OutlookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OutlookFilterApplication implements CommandLineRunner {

	private final OutlookService outlookService;

	public OutlookFilterApplication(OutlookService outlookService) {
		this.outlookService = outlookService;
	}

	public static void main(String[] args) {
		SpringApplication.run(OutlookFilterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		outlookService.testConnection();
	}
}
