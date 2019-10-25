package com.example.DemoGraphQL.bootstrap;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.DemoGraphQL.model.*;
import com.example.DemoGraphQL.repository.*;

import java.util.*;
import java.io.*;



import java.time.LocalDate;

@Component
public class LoadUtil {

    private final MeterReadingRepository meterReadingRepository;
    
    
    @Autowired
    public LoadUtil(MeterReadingRepository meterReadingRepository) {
        this.meterReadingRepository=meterReadingRepository;
    }

    
    public MeterDetails extractMeterValues(){
     // currently harded code.. need to extract from excel sheet
    	MeterDetails meterDetails=new MeterDetails();
    	meterDetails.setCustomerName("Sudbury");
    	meterDetails.setReadingDate("2019-6-01");
    	meterDetails.setMeterId("SH087469");
    	
    List <MeterReading> meterReadings=new ArrayList<MeterReading>();
    		int day=1;
   		
    		try{
    			String current = new java.io.File( "." ).getCanonicalPath();
    			System.out.println(current+"/reading.csv");
    			try (BufferedReader br = new BufferedReader(new FileReader(current+"/reading.csv"))) {
    			String line;
    			boolean firstRecord=true;
    			int maxCount=0;
    			while ((line = br.readLine()) != null && maxCount<10) {
    				if(!firstRecord){
    				String[] values = line.split(",");
        		
        			MeterReading meterReading= MeterReading.builder()
 	                .kwa(Double.valueOf(values[4].replaceAll("^\"|\"$", "")))
 	                .kw(Double.valueOf(values[3].replaceAll("^\"|\"$", "")))
 	                .kwh(Double.valueOf(values[2].replaceAll("^\"|\"$", "")))
 	                .timestamp(values[1].replaceAll("^\"|\"$", ""))
 	                .build();
    				meterReadings.add(meterReading);
    				}
    				firstRecord=false;
    				maxCount++;
    			}
    		}
    		}catch(Exception e){
    			System.out.println(e);
    		}
    		meterDetails.setMeterReadings(meterReadings);
    		return meterDetails;
    }
    
    public void storeMeterReading(){
    	   MeterDetails meterDetails=extractMeterValues();
    		meterReadingRepository.createClient(meterDetails.getCustomerName());
    		meterReadingRepository.createMeter(meterDetails.getCustomerName(),meterDetails.getMeterId(),meterDetails.getReadingDate());
    		meterDetails.getMeterReadings().forEach(meterReading -> {
    			System.out.println("processing"+meterDetails.getMeterId()+", "+meterReading.getTimestamp());
    			meterReadingRepository.createReading(meterDetails.getMeterId(),meterReading.getTimestamp(),
    											meterReading.getKwa(),meterReading.getKwh(),meterReading.getKw());
    		});
    		System.out.println(meterReadingRepository.findReadings(null));
    }
    
    
}
