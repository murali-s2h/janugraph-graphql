package com.example.DemoGraphQL.resolver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.DemoGraphQL.model.*;
import com.example.DemoGraphQL.repository.*;

public class Query implements GraphQLQueryResolver {
    private MeterReadingRepository meterReadingRepository;


    public Query(MeterReadingRepository meterReadingRepository) {
        this.meterReadingRepository=meterReadingRepository;
    }
    
    public Iterable<MeterReading> findAllReadings() {
        return meterReadingRepository.findReadings(null);
    }

}
