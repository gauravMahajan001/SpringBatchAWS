package com.batch.file.infrastructure.batch;

import com.batch.file.entity.Customer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MainFrameWriter {
    @Bean
    public JdbcBatchItemWriter<Customer> jdbcWriter(
            DataSource dataSource) {

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

        return writer;
    }
}
