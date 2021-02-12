package org.xreport.services;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xreport.entities.Parameter;
import org.xreport.entities.Report;
import org.xreport.entities.ReportResult;
import org.xreport.entities.Source;
import org.xreport.entities.SourceType;
import org.xreport.entities.UkmStore;
import org.xreport.services.dto.BuildReportDTO;

@Controller
@RequestMapping("xreport")
public class XReportServiceJ {

	@RequestMapping(method = RequestMethod.GET, value="/source-type")
	public @ResponseBody List<SourceType> getSourceTypes(){
		XReportServiceImpl s = new XReportServiceImpl();
		return s.getSourceTypes();
	}

	@RequestMapping(method = RequestMethod.GET, value="/source")
	public @ResponseBody List<Source> getSources(){
		XReportServiceImpl s = new XReportServiceImpl();
		return s.getSources();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/report/{source-type}/{source}")
	public @ResponseBody List<Report> getReports(@PathVariable("source-type") final int sourceType, @PathVariable("source") final String source){
		XReportServiceImpl s = new XReportServiceImpl();
		List<Report> reports =s.getReports(sourceType);
		for (Report r : reports){
			r.setParameters(getReportParams(r.getId(),source));
		}
		return reports;
	}

	@RequestMapping(method = RequestMethod.GET, value="/report/params/{report}/{source}")
	public @ResponseBody List<Parameter> getReportParams(@PathVariable("report") final String report, @PathVariable("source") final String source){
		XReportServiceImpl s = new XReportServiceImpl();
		List<Parameter> params= s.getReportParams(report);
		if(params!=null && source!=null && !source.isEmpty()){
			//fill parameter with items
			for (Parameter p: params){
				if (p.getListType()==1 && p.getListSql()!= null && !p.getListSql().isEmpty()){
					p.setItemsInt(s.getListInt(source, p.getListSql()));
					//hide sql from frontend
					p.setListSql("");
				}
			}
		}
		return params;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/report/build",  headers = {"content-type=application/json"})
	public @ResponseBody ReportResult buildReport(@RequestBody BuildReportDTO dto){
		XReportServiceImpl s = new XReportServiceImpl();
		return s.buildReport(dto.getReport(), dto.getSource());
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/store/{source}")
	public @ResponseBody List<UkmStore> getStores(@PathVariable("source")  String source){
		XReportServiceImpl s = new XReportServiceImpl();
		return s.getStores(source);
	}

}
