package com.rsuniverse.jobify_job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JobifyJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobifyJobApplication.class, args);
    }

}
