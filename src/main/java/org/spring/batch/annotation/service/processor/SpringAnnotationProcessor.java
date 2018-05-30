package org.spring.batch.annotation.service.processor;

import org.spring.batch.annotation.dto.ReportDto;
import org.spring.batch.annotation.model.Report;
import org.spring.batch.annotation.service.DoToDtoFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * SpringAnnotation processor.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 */
@Component
public class SpringAnnotationProcessor implements ItemProcessor<ReportDto, Report> {

	@Autowired
	private DoToDtoFactory<Report, ReportDto> doToDtoFactory;

	@Override
	public Report process(ReportDto dto) throws Exception {
		return doToDtoFactory.convertDtoToDo(dto, Report.class);
	}
}
