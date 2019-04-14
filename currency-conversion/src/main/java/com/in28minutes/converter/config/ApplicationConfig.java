package com.in28minutes.converter.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.in28minutes.converter.client")
@EnableDiscoveryClient
public class ApplicationConfig {
}
