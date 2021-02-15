package org.xreport.services.dto;

import org.xreport.entities.Report;

public class BuildReportDTO {
	private Report report;
	private String source;
	private String format;
	
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
