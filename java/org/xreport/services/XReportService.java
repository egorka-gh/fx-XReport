package org.xreport.services;

import java.util.List;

import org.granite.messaging.service.annotations.RemoteDestination;
import org.xreport.entities.Parameter;
import org.xreport.entities.Report;
import org.xreport.entities.ReportResult;
import org.xreport.entities.Source;
import org.xreport.entities.SourceType;



@RemoteDestination(id="xReportService", source="xReportService")
public interface XReportService {
	
	public List<SourceType> getSourceTypes();
	public List<Source> getSources();
	public List<Report> getReports(final int sourceType);
	public List<Parameter> getReportParams(final String report);
	public ReportResult buildReport(final Report report, String source);
}
