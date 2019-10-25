package com.example.DemoGraphQL.config;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "janusGraph.type", havingValue = "embedded")
public class EmbeddedGraphConfiguration {

    @Bean
    TinkerGraph tinkerGraph() {
        return TinkerGraph.open();
    }

    @Bean
    GraphTraversalSource graphTraversalSource(TinkerGraph tinkerGraph) {
        return tinkerGraph.traversal();
    }
}
