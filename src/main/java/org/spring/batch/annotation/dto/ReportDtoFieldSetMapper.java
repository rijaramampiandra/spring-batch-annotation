package org.spring.batch.annotation.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

/**
 * Change date string (from CSV) to java.util.Date.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 */
@Component
public class ReportDtoFieldSetMapper implements FieldSetMapper<ReportDto> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public ReportDto mapFieldSet(FieldSet fieldSet) throws BindException {

		ReportDto report = new ReportDto();
		report.setImpression(fieldSet.readString(1));
		report.setClick(fieldSet.readString(2));
		report.setEarning(fieldSet.readDouble(3));

		// default format yyyy-MM-dd
		String date = fieldSet.readString(0);
		try {
			report.setDateCsv(dateFormat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		report.setCreateDate(Calendar.getInstance().getTime());
		return report;
	}

}