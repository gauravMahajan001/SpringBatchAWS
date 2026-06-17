package com.batch.file.adapters.in.batch;

import com.batch.file.entity.batch.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/* This class is responsible for reading customer data from a CSV file and mapping it to Customer objects.
 *  It defines a FlatFileItemReader bean that reads the CSV file specified by the job parameter 'file
 *  Name'. The reader is configured to skip the first line (header) and uses a LineMapper
 *  to map each line of the CSV to a Customer object.
 */
@Slf4j
@Configuration
public class CsvRecordReader {
    @Bean
    public FlatFileItemReader<Customer> customerReader(@Value("#{jobParameters['fileName']}") String fileName) {

        log.info("Initializing FlatFileItemReader for file: {}", fileName);
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(fileName));
        reader.setLinesToSkip(1);
        reader.setName("customerReader");
        reader.setLineMapper(lineMapper());

        log.debug("FlatFileItemReader configured for file: {}", fileName);
        return reader;
    }

    @Bean
    public LineMapper<Customer> lineMapper() {

        log.trace("Creating LineMapper for Customer objects");
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

