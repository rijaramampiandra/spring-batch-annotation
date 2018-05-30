package org.spring.batch.annotation.config;

import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job launcher. Job cron configuration.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 */
@Component
public class ProcessJobLauncher {

	private static final String LOG_SEPARATOR = "{}----------------------> {}";

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessJobLauncher.class);

	@Autowired
	private Job job;

	@Autowired
	private JobLauncher jobLauncher;

	@Value("${file.path.csv}")
	private String filePathCsv;

	// <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year>
	// <command>
	// See : http://www.baeldung.com/cron-expressions
	// @Scheduled(cron = "0 0/5 * * * *")
	@Scheduled(cron = "${cron.expression}")
	void launchSol30ToDatabaseJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException {
		LOGGER.info(LOG_SEPARATOR, "\t\t", "Starting SttProcessJob job");

		JobParameters jobParameters = new JobParametersBuilder().addDate("DATE_VAL", Calendar.getInstance().getTime())
				.addString("UUID", UUID.randomUUID().toString()).toJobParameters();

		final JobExecution execution = jobLauncher.run(job, jobParameters);

		LOGGER.info("Exit Status : {}", execution.getStatus());
		LOGGER.info(LOG_SEPARATOR, "\t\t", "Stopping SttProcessJob job");

	}
}
