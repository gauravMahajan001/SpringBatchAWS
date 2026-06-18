package com.batch.file.infrastructure.batch;

import com.batch.file.entity.batch.AuditRecord;
import com.batch.file.entity.batch.FailedRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DynamoDbConfig {
    @Value("${aws.dynamodb.audit-table-name}")
    private String auditTableName;
    @Value("${aws.dynamodb.failed-table-name}")
    private String failedTableName;

    @Bean
    public DynamoDbTable<AuditRecord> auditRecordTable(
            DynamoDbEnhancedClient enhancedClient) {

        return enhancedClient.table(
                auditTableName,
                TableSchema.fromBean(AuditRecord.class)
        );
    }

    /*@Bean
    @Qualifier("failedRecordTable")
    public DynamoDbTable<FailedRecord> failedRecordTable(
            DynamoDbEnhancedClient enhancedClient) {

        return enhancedClient.table(
                failedTableName,
                TableSchema.fromBean(FailedRecord.class)
        );
    }*/

    @Bean
    public DynamoDbClient dynamoDbClient() {
        // todo need make regiom dynamic
        log.info("Initializing DynamoDB client with region: {}", Region.US_EAST_1);
        return DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
