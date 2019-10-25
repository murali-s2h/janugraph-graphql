package com.example.DemoGraphQL.bootstrap;

import com.example.DemoGraphQL.config.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Slf4j
@Component
public class LoadMeterDetails {

    private final GraphTraversalSource g;
    private final LoadUtil util;

    @Autowired
    public LoadMeterDetails(
            GraphTraversalSource g,
            LoadUtil util) {

        this.g = g;
        this.util = util;
    }

    @PostConstruct
    private void onLoad() throws Exception
    {
        try {
        	util.storeMeterReading();
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
