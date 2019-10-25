package com.example.DemoGraphQL.repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.structure.io.IoCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.DemoGraphQL.model.*;
import com.example.DemoGraphQL.repository.ModelConstants.METER_READING;
import java.util.*;


@Repository
public class MeterReadingRepository {

	
		private final GraphTraversalSource g;
	    private final Client client;

	    @Autowired
	    public MeterReadingRepository(
	            GraphTraversalSource g,
	            @Autowired(required = false) Client client) {
	        this.g = g;
	        this.client = client;
	    }

	    
	    public void createClient(String clientName){
		    		 Long count =g.V().has(METER_READING.APP_LABEL).count().next();
	    		 	System.out.println(count);
	    		
	    		 	if(count==0){
	    			Vertex applicationVertex = g.addV(METER_READING.APP_LABEL)
	    								.property(METER_READING.APP_NAME, "rhycom")
	    								.property(METER_READING.CREATED_DATE,new Date().toString())
	    								.next();
	    		 	}
	    			
	    		 	count = g.V().has(METER_READING.CUSTOMER_LABEL,METER_READING.CUSTOMER_NAME,clientName).count().next();
	     	    System.out.println(count);
	     		
	     	    if(count==0){
	     			Vertex customerVertex = g.addV(METER_READING.CUSTOMER_LABEL)
	    								.property(METER_READING.CUSTOMER_NAME,clientName)
	    								.property(METER_READING.CUSTOMER_ADDRESS,"some address")
	    								.next();
	   
	     			Edge applicationCustomerEdge = g.V().has(METER_READING.CUSTOMER_LABEL,METER_READING.CUSTOMER_NAME,clientName)
		        		  			  .as("client")
		        		  			  .V().has(METER_READING.APP_LABEL,METER_READING.APP_NAME, "rhycom")
		        		  			  .addE("contract").to("client")
		        		  			  .next();
	     			
	    		 	System.out.println(applicationCustomerEdge);
    		} 
		    }
	    
	    public void createMeter(String clientName, String meterId, String date) {
	    
	    				Vertex  meterReadingVertext= g.addV(METER_READING.METER_LABEL)
	     	    							.property(METER_READING.METER_ID,meterId)
	     	    							.property(METER_READING.METER_TRANSACTION_DATE, date)
	     	    							.next();
	    				
	    				
	    				Edge clientMeterReadingEdge =g.V().has(METER_READING.METER_LABEL,METER_READING.METER_ID,meterId)
	    								.as("meter_details")
	    								.V().has(METER_READING.CUSTOMER_LABEL,METER_READING.CUSTOMER_NAME,clientName)
	    								.addE("connection").to("meter_details")
	    								.next();
	    				
	    				System.out.println(clientMeterReadingEdge);

	    						
	    		}
	    
	    public void createReading(String meterId, String timestamp, double kwa, double kwh, double kw){
			System.out.println(meterId+" timestamp");

	    	
	    			Vertex readingVertext = g.addV(METER_READING.READING_LABEL)
		   				.property(METER_READING.READING_TIMESTAMP,timestamp)
		   				.property(METER_READING.READING_KVA,kwa)
		   				.property(METER_READING.READING_KWH,kwh)
		   				.property(METER_READING.READING_KW,kw)
						.next();
	 
	    	  		Edge edge = g.V().has(METER_READING.READING_LABEL,METER_READING.READING_TIMESTAMP,timestamp).as("readings")
	    	  					.V().has(METER_READING.METER_LABEL,METER_READING.METER_ID,meterId)
	    	  					.addE("usage").to("readings")
	    	  					.next();
	    	  		
		     	    System.out.println(edge);

	    }

	    
	    
	   public List<MeterReading> findReadings(Optional<String> firstName) {
	 
		   	System.out.println(g.V().hasLabel("Readings").count().next());
	        GraphTraversal<?, ?> traversal = g.V().hasLabel("Readings");

		       System.out.println(g);
	       System.out.println(g.getGraph());
	       
	 	   try {
	 		  
	 		  g.getGraph().io(IoCore.graphson()).writeGraph("Sudbury11.json");
				g.getGraph().io(IoCore.graphml()).writeGraph("Sudbury11.graphml");
		 	   } catch (IOException e) {
		 		   // TODO Auto-generated catch block
		 		   e.printStackTrace();
		 	   }
			   
	 	   
	        return traversal.valueMap(true)
	                .toStream()
	                .map(this::toMeterReading)
	                .collect(Collectors.toList());
	        
	    }
	    
	    private MeterReading toMeterReading(Map<String, Object> valueMap) {
	    		System.out.println(valueMap);
	    		VertexValuesWrapper wrapper = VertexValuesWrapper.fromValueMap(valueMap);
	        return MeterReading.builder()
	                .kwa(wrapper.getDoubleValue(METER_READING.READING_KVA))
	                .kw(wrapper.getDoubleValue(METER_READING.READING_KW))
	                .kwh(wrapper.getDoubleValue(METER_READING.READING_KWH))
	                .timestamp(wrapper.getStringValue(METER_READING.READING_TIMESTAMP))
	                .build();
	    }

}
