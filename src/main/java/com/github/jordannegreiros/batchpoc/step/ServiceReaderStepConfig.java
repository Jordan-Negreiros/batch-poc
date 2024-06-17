package com.github.jordannegreiros.batchpoc.step;

import com.github.jordannegreiros.batchpoc.domain.User;
import com.github.jordannegreiros.batchpoc.entity.ClientEntity;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ServiceReaderStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Value("${chunkSize}")
    private int chunkSize;

    public ServiceReaderStepConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }


    @Bean
    public Step step(
            ItemReader<User> reader,
            ItemProcessor<User, ClientEntity> processor,
            ItemWriter<ClientEntity> writer
    ) {
        return new StepBuilder("serviceReaderStep", jobRepository)
                .<User, ClientEntity>chunk(chunkSize, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
