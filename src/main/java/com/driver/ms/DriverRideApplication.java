package com.driver.ms;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableHystrix
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
@EnableConfigurationProperties
@SpringBootApplication
public class DriverRideApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverRideApplication.class, args);
    }

    @Bean
    public Sampler sampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
