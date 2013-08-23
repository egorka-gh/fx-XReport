package org.xreport.services;

import java.util.List;

import org.granite.messaging.service.annotations.RemoteDestination;
import org.xreport.entities.SourceType;



@RemoteDestination(id="xReportService", source="xReportService")
public interface XReportService {
	
	public List<SourceType> getSourceTypes();
}
