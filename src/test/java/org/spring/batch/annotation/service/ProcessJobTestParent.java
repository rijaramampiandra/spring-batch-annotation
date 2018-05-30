package org.spring.batch.annotation.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.batch.annotation.config.TestBatchConfig;
import org.spring.batch.annotation.model.Report;
import org.spring.batch.annotation.repository.ReportRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestBatchConfig.class })
@ActiveProfiles("test")
public class ProcessJobTestParent {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessJobTestParent.class);

	@Autowired
	private Environment environment;

	@Autowired
	protected JobLauncherTestUtils testUtils;

	@Autowired
	protected Job job;

	@Autowired
	protected ReportRepository reportRepository;

	@Before
	public void setUp() throws Exception {
		// vider les tables en cas de test.
		TestBatchConfig.clearDataTest(environment, reportRepository);

		LOGGER.info("\t\t\t\t---------------------------------------------------- Call set UP."
				+ reportRepository.findAll().size() + " --------------------------------------");
	}

	@Test
	public void testSuccessJob() throws Exception {
		final JobExecution result = testUtils.getJobLauncher().run(job, testUtils.getUniqueJobParameters());
		Assert.assertNotNull(result);

		final List<Report> response = reportRepository.findAll();

		LOGGER.info("\t\t\t\t---------------------------------------------------- Call set UP." + response.size()
				+ " --------------------------------------");

		Assert.assertEquals("We shoul'd have 5 lines on the DB (h2 memory).", 5, response.size());
		Assert.assertEquals("We shoul'd have \"472,385\" on the DB (h2 memory).", "472,385",
				response.get(4).getImpression());
	}

}
