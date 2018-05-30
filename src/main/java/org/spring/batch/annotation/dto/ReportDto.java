package org.spring.batch.annotation.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class ReportDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dateCsv;

	private String impression;
	private String click;
	private Double earning;
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getImpression() {
		return impression;
	}

	public void setImpression(String impression) {
		this.impression = impression;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public Double getEarning() {
		return earning;
	}

	public void setEarning(Double earning) {
		this.earning = earning;
	}

	public Date getDateCsv() {
		return dateCsv;
	}

	public void setDateCsv(Date dateCsv) {
		this.dateCsv = dateCsv;
	}

}
