package org.xreport.services;

import java.util.List;

import org.granite.messaging.service.annotations.RemoteDestination;
import org.xreport.entities.ListItemInt;
import org.xreport.entities.Parameter;
import org.xreport.entities.Report;
import org.xreport.entities.ReportResult;
import org.xreport.entities.Source;
import org.xreport.entities.SourceType;
import org.xreport.entities.UkmStore;



@RemoteDestination(id="xReportService", source="xReportService")
public interface XReportService {
	
	public List<SourceType> getSourceTypes();
	public List<Source> getSources();
	public List<Report> getReports(final int sourceType);
	public List<Parameter> getReportParams(final String report);
	public List<UkmStore> getStores(String source);
	public ReportResult buildReport(final Report report, String source);
	List<ListItemInt> getListInt(String source, String sql);
}
