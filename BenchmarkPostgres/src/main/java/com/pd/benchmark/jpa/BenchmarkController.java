package com.pd.benchmark.jpa;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pd.benchmark.dataobjects.BenchmarkConstants;

@RestController
public class BenchmarkController {
	@Autowired
	private BenchmarkLogicTarget benchmarkLogicTarget;
	
	private static final DecimalFormat FORMATTER = new DecimalFormat("###,###,###,###");
	
	@GetMapping("/setup")
	public String setup() {
		System.out.println("Enter controller setup "+LocalDateTime.now());
		String setupResponse= this.benchmarkLogicTarget.setup();
		System.out.println(setupResponse);
		return setupResponse;
	}

	
	@GetMapping("/doinserts1")
	public String doinserts1() {
		System.out.println("Enter controller doinserts1 "+LocalDateTime.now());
		String insertResponse = this.benchmarkLogicTarget.doInserts(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 1);
		return insertResponse;
	}

	@GetMapping("/doinserts2")
	public String doinserts2() {
		System.out.println("Enter controller doinserts2 "+LocalDateTime.now());
		String insertResponse = this.benchmarkLogicTarget.doInserts(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 2);
		return insertResponse;
	}

	@GetMapping("/doinserts3")
	public String doinserts3() {
		System.out.println("Enter controller doinserts3 "+LocalDateTime.now());
		String insertResponse = this.benchmarkLogicTarget.doInserts(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 3);
		return insertResponse;
	}

	@GetMapping("/doinserts4")
	public String doinserts4() {
		System.out.println("Enter controller doinserts4 "+LocalDateTime.now());
		String insertResponse = this.benchmarkLogicTarget.doInserts(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 4);
		return insertResponse;
	}
	
	@GetMapping("/doinserts5")
	public String doinserts5() {
		System.out.println("Enter controller doinserts5 "+LocalDateTime.now());
		String insertResponse = this.benchmarkLogicTarget.doInserts(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 5);
		return insertResponse;
	}

	@GetMapping("/doinserts6")
	public String doinserts6() {
		System.out.println("Enter controller doinserts6 "+LocalDateTime.now());
		String insertResponse = this.benchmarkLogicTarget.doInserts(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 6);
		return insertResponse;
	}

	@GetMapping("/doinserts7")
	public String doinserts7() {
		System.out.println("Enter controller doinserts7 "+LocalDateTime.now());
		String insertResponse = this.benchmarkLogicTarget.doInserts(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 7);
		return insertResponse;
	}

	@GetMapping("/doinserts8")
	public String doinserts8() {
		System.out.println("Enter controller doinserts8 "+LocalDateTime.now());
		String insertResponse = this.benchmarkLogicTarget.doInserts(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 8);
		return insertResponse;
	}
	
	@GetMapping("/readUpdate1")
	public String readUpdate1() {
		System.out.println("Enter readUpdate1 "+LocalDateTime.now());
		String readUpdateResponse = this.benchmarkLogicTarget.doReadUpdate(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 1);
		return readUpdateResponse;
	}
	@GetMapping("/readUpdate2")
	public String readUpdate2() {
		System.out.println("Enter readUpdate2 "+LocalDateTime.now());
		String readUpdateResponse = this.benchmarkLogicTarget.doReadUpdate(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 2);
		return readUpdateResponse;
	}
	@GetMapping("/readUpdate3")
	public String readUpdate3() {
		System.out.println("Enter readUpdate3 "+LocalDateTime.now());
		String readUpdateResponse = this.benchmarkLogicTarget.doReadUpdate(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 3);
		return readUpdateResponse;
	}
	@GetMapping("/readUpdate4")
	public String readUpdate4() {
		System.out.println("Enter readUpdate4 "+LocalDateTime.now());
		String readUpdateResponse = this.benchmarkLogicTarget.doReadUpdate(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 4);
		return readUpdateResponse;
	}
	@GetMapping("/readUpdate5")
	public String readUpdate5() {
		System.out.println("Enter readUpdate5 "+LocalDateTime.now());
		String readUpdateResponse = this.benchmarkLogicTarget.doReadUpdate(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 5);
		return readUpdateResponse;
	}
	@GetMapping("/readUpdate6")
	public String readUpdate6() {
		System.out.println("Enter readUpdate6 "+LocalDateTime.now());
		String readUpdateResponse = this.benchmarkLogicTarget.doReadUpdate(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 6);
		return readUpdateResponse;
	}
	@GetMapping("/readUpdate7")
	public String readUpdate7() {
		System.out.println("Enter readUpdate7 "+LocalDateTime.now());
		String readUpdateResponse = this.benchmarkLogicTarget.doReadUpdate(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 7);
		return readUpdateResponse;
	}
	@GetMapping("/readUpdate8")
	public String readUpdate8() {
		System.out.println("Enter readUpdate8 "+LocalDateTime.now());
		String readUpdateResponse = this.benchmarkLogicTarget.doReadUpdate(BenchmarkConstants.BENCHMARK_TARGET_POSTGRES, 8);
		return readUpdateResponse;
	}

	@GetMapping("/report")
	public String report() {
		System.out.println("Enter report "+LocalDateTime.now());
		String reportResponse = this.benchmarkLogicTarget.reportStats();
		return reportResponse;
	}

	@GetMapping("/findByLastName{name}")
	public String findByLastName(String name) {
		// ...
		System.out.println("Enter findByLastName "+LocalDateTime.now());
		long start = System.nanoTime();
		List<PersonPg> persons = this.benchmarkLogicTarget.findByLastName(name);
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
		
		String timeElapsedStr = FORMATTER.format(timeElapsed)+" Nanos, ";
		String responseMsgTmplt = "done with finaByLastName for %s -- %s items returned, elapsed time: %s."; 
		String responseTxt = String.format(responseMsgTmplt, name, persons.size(),timeElapsedStr);
		System.out.println(responseTxt);
		return responseTxt;
	}
	
}