package com.batch.file.infrastructure.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;

@Configuration
public class DynamoDbClient {
    @Bean
    public software.amazon.awssdk.services.dynamodb.DynamoDbClient dynamoDbClient() {
        // todo need make regiom dynamic
        return software.amazon.awssdk.services.dynamodb.DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }

}
