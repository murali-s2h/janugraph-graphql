package com.example.DemoGraphQL.model;

import java.util.ArrayList;
import java.util.List;

import com.example.DemoGraphQL.model.MeterReading.MeterReadingBuilder;
import lombok.Builder;
import lombok.Data;

@Data

public class MeterDetails {

	
	public MeterDetails(){}
	
	private String customerName;
	private String readingDate;
	private String meterId;
	private List <MeterReading> meterReadings=new ArrayList<MeterReading>();
	
}
