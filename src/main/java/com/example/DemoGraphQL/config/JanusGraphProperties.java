package com.example.DemoGraphQL.config;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Getter
@Setter
@Configuration
@ConfigurationProperties("janusGraph")
public class JanusGraphProperties {

    private String hosts;

    private int port = 8182;

    private String type = "remote";

    public Set<String> getHosts() {
        return hosts == null ? null : ImmutableSet.copyOf(Splitter.on(",").split(hosts));
    }
}
