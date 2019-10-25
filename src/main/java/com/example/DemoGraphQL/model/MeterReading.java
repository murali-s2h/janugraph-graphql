package com.example.DemoGraphQL.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class MeterReading {

	private String date;
	private String  timestamp;
	private Double  kwh;
	private Double  kw;
	private Double  kwa;
	
	
}
