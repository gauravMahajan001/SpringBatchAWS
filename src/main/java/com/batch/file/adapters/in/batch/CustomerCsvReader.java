package com.batch.file.adapters.in.batch;

import com.batch.file.entity.Customer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
public class CustomerCsvReader {
    @Bean
    public FlatFileItemReader<Customer> customerReader(@Value("#{jobParameters['fileName']}") String fileName) {

        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(fileName));
        reader.setLinesToSkip(1);
        reader.setName("customerReader");
        reader.setLineMapper(lineMapper());

        return reader;
    }

    @Bean
    public LineMapper<Customer> lineMapper() {

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("refNumber", "firstName", "lastName", "gender", "amount", "referenceDate");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(Customer.class);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}

