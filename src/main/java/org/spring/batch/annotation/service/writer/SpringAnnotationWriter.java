package org.spring.batch.annotation.service.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.batch.annotation.model.Report;
import org.spring.batch.annotation.repository.ReportRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Get ReportDTO and write it on the DB.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 */
@Service
public class SpringAnnotationWriter implements ItemWriter<Report> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringAnnotationWriter.class);

	@Autowired
	private ReportRepository reportRepository;

	@Override
	public void write(List<? extends Report> leads) throws Exception {
		LOGGER.info("-------------------------- Step : SpringAnnotationWriter ----------------------------------");
		if (leads != null && leads.size() > 0) {
			reportRepository.save(leads);
		}
	}
}