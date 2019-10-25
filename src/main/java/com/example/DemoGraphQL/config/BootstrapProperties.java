package com.example.DemoGraphQL.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("bootstrap")
public class BootstrapProperties {
    private Boolean loadSchema = true;
    private Boolean loadData = true;
}
