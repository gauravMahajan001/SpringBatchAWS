package com.batch.file.infrastructure.batch;

import com.batch.file.entity.batch.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
/*
 * This class is responsible for configuring the JdbcBatchItemWriter for writing Customer data to the MainFrame database.
 * It defines a bean that sets up the SQL insert statement and maps the Customer properties to the corresponding database columns.
 */
public class MainframeWriter {
    @Bean
    public JdbcBatchItemWriter<Customer> jdbcWriter(
            DataSource dataSource) {

        log.info("Initializing JdbcBatchItemWriter for MainFrame Customer table");
        JdbcBatchItemWriter<Customer> writer =
                new JdbcBatchItemWriter<>();

        writer.setDataSource(dataSource);

        writer.setSql("""
                INSERT INTO EMPLOYEE
                (
                    ID,
                    REF_NUMBER,
                    FIRST_NAME,
                    LAST_NAME,
                    GENDER,
                    AMOUNT,
                    REFERENCE_DATE
                )
                VALUES
                (
                    :id,
                    :refNumber,
                    :firstName,
                    :lastName,
                    :gender,
                    :amount,
                    :referenceDate
                )
                """);

        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());

        writer.afterPropertiesSet();

        log.debug("JdbcBatchItemWriter configured successfully");
        return writer;
    }
}
