package com.example.outlookfilter;

import com.example.outlookfilter.service.OutlookService;
import com.example.outlookfilter.service.SchedulerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OutlookFilterApplication implements CommandLineRunner {

	private final OutlookService outlookService;
	private final SchedulerService schedulerService;

	public OutlookFilterApplication(OutlookService outlookService, SchedulerService schedulerService) {
		this.outlookService = outlookService;
		this.schedulerService = schedulerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(OutlookFilterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		outlookService.testConnection();
		schedulerService.startOrUpdateSchedule(2);

	}
}
