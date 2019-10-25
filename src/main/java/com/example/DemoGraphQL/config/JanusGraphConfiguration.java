package com.example.DemoGraphQL.config;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.MessageSerializer;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV1d0;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.util.empty.EmptyGraph;
import org.janusgraph.graphdb.tinkerpop.JanusGraphIoRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "janusGraph.type", havingValue = "remote")
public class JanusGraphConfiguration {

    @Bean
    public MessageSerializer messageSerializer() {
        Map<String, Object> config = new HashMap<>();
        config.put("ioRegistries", Collections.singletonList(JanusGraphIoRegistry.class.getName()));
        GryoMessageSerializerV1d0 serializer = new GryoMessageSerializerV1d0();
        serializer.configure(config, null);

        return serializer;
    }

    @Bean
    public Cluster janusCluster(
            MessageSerializer messageSerializer,
            JanusGraphProperties properties) {

        Cluster.Builder clusterBuilder = Cluster.build()
                .port(properties.getPort())
                .serializer(messageSerializer);

        for (String contactPoint : properties.getHosts()) {
            clusterBuilder.addContactPoint(contactPoint);
        }

        return clusterBuilder.create();
    }

    @Bean
    public Client gremlinClient(Cluster cluster) {
        return cluster.connect();
    }

    @Bean(name = "remoteTraversalSource")
    public GraphTraversalSource traversalSource(Cluster cluster) {
        return EmptyGraph.instance()
                .traversal()
                .withRemote(DriverRemoteConnection.using(cluster));
    }
}
