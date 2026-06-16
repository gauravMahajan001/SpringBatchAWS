package com.batch.file.infrastructure.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;

@Slf4j
@Configuration
public class DynamoDbClient {
    @Bean
    public software.amazon.awssdk.services.dynamodb.DynamoDbClient dynamoDbClient() {
        // todo need make regiom dynamic
        log.info("Initializing DynamoDB client with region: {}", Region.US_EAST_1);
        return software.amazon.awssdk.services.dynamodb.DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

}
