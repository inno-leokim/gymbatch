package com.brand13.gym.gymbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@EnableBatchProcessing
@RequiredArgsConstructor
@SpringBootApplication
public class GymBatchApplication {

	
	// private JobBuilderFactory jobBuilderFactory;
	// private final StepBuilderFactory stepBuilderFactory;

	// public GymBatchApplication(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory){
		// this.jobBuilderFactory = jobBuilderFactory;
		// this.stepBuilderFactory = stepBuilderFactory;
	// }
	@Bean
	public Tasklet myTasklet() {
		return (contribution, chunkContext) -> {
					System.out.println("Execute PassStep");
					return RepeatStatus.FINISHED;
				};
	}
	@Bean
	public Step passStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
		// return this.stepBuilderFactory.get("passStep")
				// .tasklet((contribution, chunkContext) -> {
				// 	System.out.println("Execute PassStep");
				// 	return RepeatStatus.FINISHED;
				// }).build();
		return new StepBuilder("passStep", jobRepository)
				.tasklet(myTasklet(), transactionManager).build();
	}

	@Bean
	public Job passJob(JobRepository jobRepository, Step step) {
		// return this.jobBuilderFactory.get("passJob")
		// 			.start(passStep())
		// 			.build();
		return new JobBuilder("passJob", jobRepository).start(step).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GymBatchApplication.class, args);
	}

}
