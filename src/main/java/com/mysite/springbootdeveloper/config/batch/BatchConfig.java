package com.mysite.springbootdeveloper.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public JobBuilder jobBuilder() {
        return new JobBuilder("refreshTokenCleanupJob", jobRepository);
    }

    @Bean
    public StepBuilder stepBuilder() {
        return new StepBuilder("deleteExpiredRefreshTokensStep", jobRepository);
    }
}