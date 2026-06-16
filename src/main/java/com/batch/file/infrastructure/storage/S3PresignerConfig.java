package com.batch.file.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.regions.Region;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class S3PresignerConfig {

    @Value("${aws.region:AP_SOUTH_1}")
    private String awsRegion;

    @Bean
    public S3Presigner s3Presigner() {
        log.info("Initializing S3Presigner bean with region: {}", awsRegion);
        return S3Presigner.builder()
                .region(Region.of(awsRegion))
                .build();
    }
}

