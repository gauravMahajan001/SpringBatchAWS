package com.batch.file.infrastructure.batch;

import com.batch.file.entity.batch.AuditRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Slf4j
@Configuration
public class DynamoDbConfig {
    @Value("${aws.dynamodb.audit-table-name}")
    private String auditTableName;
    @Value("${aws.region.name}")
    private String awsRegion;
    @Value("${aws.dynamodb.max-connection}")
    private int maxConnections;

    @Bean
    public DynamoDbTable<AuditRecord> auditRecordTable(
            DynamoDbEnhancedClient enhancedClient) {

        return enhancedClient.table(
                auditTableName,
                TableSchema.fromBean(AuditRecord.class)
        );
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(
            DynamoDbClient dynamoDbClient) {

        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .httpClientBuilder(
                        ApacheHttpClient.builder()
                                .maxConnections(maxConnections)
                )
                .region(Region.of(awsRegion))
                .build();
    }
}
