package com.pd.benchmark.dataaccesslog;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BenchmarkController {
	@Autowired
	private DataAccessLogService dataAccessLogService;
	
	@GetMapping("/populateDataAccessLog")
	public String populateDataAccessLog() {
		System.out.println("Enter controller setup "+LocalDateTime.now());
		String setupResponse= this.dataAccessLogService.populateDataAccessLog();
		System.out.println(setupResponse);
		return setupResponse;
	}	
}