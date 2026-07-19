package com.divyaksh.cap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CodingAssessmentPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingAssessmentPlatformApplication.class, args);
    }

}

