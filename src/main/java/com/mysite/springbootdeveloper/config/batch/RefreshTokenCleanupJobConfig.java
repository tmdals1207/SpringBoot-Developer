package com.mysite.springbootdeveloper.config.batch;

import com.mysite.springbootdeveloper.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableBatchProcessing
public class RefreshTokenCleanupJobConfig {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job refreshTokenCleanupJob() {
        return new JobBuilder("refreshTokenCleanupJob", jobRepository)
                .start(deleteExpiredRefreshTokensStep())
                .build();
    }

    @Bean
    public Step deleteExpiredRefreshTokensStep() {
        return new StepBuilder("deleteExpiredRefreshTokensStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    LocalDateTime now = LocalDateTime.now();
                    int deletedCount = refreshTokenRepository.deleteByExpiredAtBefore(now);
                    log.info("만료된 리프레시 토큰 {}개 삭제 완료", deletedCount);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}