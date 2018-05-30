package org.spring.batch.annotation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.batch.annotation.dto.ReportDto;
import org.spring.batch.annotation.dto.ReportDtoFieldSetMapper;
import org.spring.batch.annotation.exceptions.BatchWsException;
import org.spring.batch.annotation.model.Report;
import org.spring.batch.annotation.service.processor.SpringAnnotationProcessor;
import org.spring.batch.annotation.service.writer.SpringAnnotationWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * Job config.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 */
@Configuration
public class ProcessJobConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessJobConfig.class);

	private final static String LOG_SEPARATOR = "{}----------------------------- {} ----------------------------------";

	private final static String JOB_NAME = "springAnnotationJob";

	@Value("${job.chunk}")
	private Integer chunkConfig;

	@Value("${file.path.csv}")
	private String filePathCsv;

	@Autowired
	private JobBuilderFactory jobBuilders;

	@Autowired
	private StepBuilderFactory stepBuilders;

	@Bean
	public Job processJob() throws BatchWsException {
		return jobBuilders.get(JOB_NAME).start(stepCallSpringAnnotation()).build();
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	/**********************************************
	 * BEGIN : STEP SpringAnnotationBatch.
	 **********************************************/
	/**
	 * 
	 * @return the step.
	 * @throws BatchWsException
	 *             the exc.
	 */
	@Bean
	public Step stepCallSpringAnnotation() throws BatchWsException {
		LOGGER.info(LOG_SEPARATOR, "\n\n", "Step : SpringAnnotationBatch");
		return stepBuilders.get("springAnnotation").<ReportDto, Report>chunk(chunkConfig).reader(reader())
				.processor(springAnnotationProcessor()).writer(springAnnotationWriter()).allowStartIfComplete(true)
				.build();
	}

	@Bean
	public ItemReader<ReportDto> reader() {
		FlatFileItemReader<ReportDto> reader = new FlatFileItemReader<ReportDto>();
		reader.setResource(new ClassPathResource(filePathCsv));
		reader.setLinesToSkip(1);
		reader.setLineMapper(new DefaultLineMapper<ReportDto>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "dateCsv", "impression", "click", "earning" });
					}
				});
				setFieldSetMapper(new ReportDtoFieldSetMapper());
			}
		});
		return reader;
	}

	/**
	 * 
	 * @return the processor.
	 */
	@Bean
	@StepScope
	ItemProcessor<ReportDto, Report> springAnnotationProcessor() {
		return new SpringAnnotationProcessor();
	}

	/**
	 * 
	 * @return the writer.
	 */
	@Bean
	ItemWriter<Report> springAnnotationWriter() {
		return new SpringAnnotationWriter();
	}
	/**********************************************
	 * END : STEP SpringAnnotationBatch.
	 **********************************************/
}
