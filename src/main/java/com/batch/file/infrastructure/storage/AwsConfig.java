package com.batch.file.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    @Value("${aws.region.name}")
    private String awsRegion;

    @Bean
    public S3Presigner awsS3Presigner() {
        log.info("Initializing S3Presigner bean with region: {}", awsRegion);
        return S3Presigner.builder()
                .region(Region.of(awsRegion))
                .build();
    }

    @Bean
    public S3Client awsS3Client() {
        log.info("Initializing S3Client bean with region: {}", awsRegion);
        return S3Client.builder()
                .region(Region.of(awsRegion))
                .httpClientBuilder(
                        ApacheHttpClient.builder()
                                .maxConnections(100)
                                .connectionTimeout(Duration.ofSeconds(10))
                                .socketTimeout(Duration.ofSeconds(30))
                )
                .build();
    }
}

