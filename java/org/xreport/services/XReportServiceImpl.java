package org.xreport.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.granite.context.GraniteContext;
import org.granite.messaging.webapp.HttpGraniteContext;
import org.sansorm.OrmElf;
import org.sansorm.SqlClosure;
import org.sansorm.SqlClosureElf;
import org.sansorm.internal.OrmWriter;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xreport.constants.Constants;
import org.xreport.entities.Parameter;
import org.xreport.entities.Report;
import org.xreport.entities.ReportResult;
import org.xreport.entities.ReportSchedule;
import org.xreport.entities.Source;
import org.xreport.entities.SourceType;
import org.xreport.entities.UkmStore;
import org.xreport.util.ConnectionFactory;
import org.xreport.util.ValueDistributorImpl;

import com.reporter.XlsReporter;
import com.reporter.document.XLSDocumentWriter;


@Service("xReportService")
public class XReportServiceImpl implements XReportService {

	//private Connection conn;
	/*
	public XlsReportServiceImpl() {
		super();
		try {
			conn= ConnectionFactory.getConnection();
		} catch (SQLException e) {
			conn=null;
			e.printStackTrace();
		}
	}
	*/

	@Override
	public List<SourceType> getSourceTypes() {
		return new SqlClosure<List<SourceType>>(ConnectionFactory.getDataSource()) {
			public List<SourceType> execute(Connection connection) {
				try {
					PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM xrep.source_type ORDER BY id");
					return OrmElf.statementToList(pstmt, SourceType.class);
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.execute();		
	}

	@Override
	public List<Source> getSources() {
		return new SqlClosure<List<Source>>(ConnectionFactory.getDataSource()) {
			public List<Source> execute(Connection connection) {
				try {
					PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM xrep.source ORDER BY id DESC");
					return OrmElf.statementToList(pstmt, Source.class);
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.execute();		
	}

	@Override
	public List<Report> getReports(final int sourceType) {
		return new SqlClosure<List<Report>>(ConnectionFactory.getDataSource()) {
			public List<Report> execute(Connection connection) {
				try {
					PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM xrep.report WHERE src_type = ?");
					return OrmElf.statementToList(pstmt, Report.class, sourceType);
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.execute();		
	}

	@Override
	public List<Parameter> getReportParams(final String report) {
		return new SqlClosure<List<Parameter>>(ConnectionFactory.getDataSource()) {
			public List<Parameter> execute(Connection connection) {
				try {
					PreparedStatement pstmt = connection.prepareStatement("SELECT p.* FROM xrep.report_params rp INNER JOIN parameter p ON p.id = rp.parameter WHERE rp.report = ?");
					return OrmElf.statementToList(pstmt, Parameter.class, report);
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.execute();		
	}

	
	@Override
	public List<UkmStore> getStores(String source) {
		return new SqlClosure<List<UkmStore>>(ConnectionFactory.getDataSource(source)) {
			public List<UkmStore> execute(Connection connection) {
				try {
					PreparedStatement pstmt = connection.prepareStatement("SELECT store_id, name, inn FROM ukmserver.trm_in_store st WHERE st.deleted = 0");
					return OrmElf.statementToList(pstmt, UkmStore.class);
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.execute();		
	}

	public List<ReportSchedule> getSchedule() {
		return new SqlClosure<List<ReportSchedule>>(ConnectionFactory.getDataSource()) {
			public List<ReportSchedule> execute(Connection connection) {
				try {
					String sql="SELECT * FROM xrep.report_shedule rs"+
								 " WHERE rs.active = 1"+
								   " AND rs.week_day IN (0, DAYOFWEEK(CURRENT_DATE()) + 1)"+
								   " AND rs.run_after_hour <= HOUR(NOW())"+
								   " AND (rs.last_run IS NULL OR rs.last_run < CURRENT_DATE())"+
								   " AND rs.send_to IS NOT NULL";
					PreparedStatement pstmt = connection.prepareStatement(sql);
					return OrmElf.statementToList(pstmt, ReportSchedule.class);
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.execute();		
	}

	public void setScheduleComplited(int sheduleId) {
		String sql="UPDATE xrep.report_shedule rs SET rs.last_run=NOW() WHERE rs.id=?";
		Connection connection = null;
		try {
			connection=ConnectionFactory.getConnection();
			OrmWriter.executeUpdate(connection, sql, sheduleId);
		} catch (SQLException e) {
			//result.setErrMesage(e.getMessage());
			e.printStackTrace();
		}finally{
			SqlClosureElf.quietClose(connection);
		}
	}
	
	@Override
	public ReportResult buildReport(final Report report, String source){
		HttpGraniteContext ctx = (HttpGraniteContext)GraniteContext.getCurrentInstance();

		String reportPath = ctx.getServletContext().getRealPath("/");
		reportPath+="/"+Constants.REPORTS_FOLDER+"/";

		String outPath=(String) ctx.getServletContext().getAttribute(Constants.OUT_FOLDER_SESSION_ATTRIBUTE);
		
		String outURL = (String) ctx.getServletContext().getAttribute(Constants.SESSION_ID_ATTRIBUTE);
		outURL=Constants.URL_REPORT_BASE_URL+"/"+outURL+"/";

		return buildInternal(report,source,"", reportPath, outPath, outURL);
	}

	public ReportResult buildLocal(String reportId, String source, String outSuffix, String reportPath, String outPath) {
		Report report= new Report();
		report.setId(reportId);
		report.setParameters(null);
		return buildInternal(report,source,outSuffix, reportPath, outPath, "");
	}

	protected ReportResult buildInternal(final Report report, String source, String outSuffix, String reportPath, String outPath, String outURL){ // , ServletContext servletContext) { //not a case
		ReportResult result= new ReportResult();
		if(report == null){
			result.assignError("null report");
			return result;
		}
		result.setId(report.getId());
		
		//HttpGraniteContext ctx = (HttpGraniteContext)GraniteContext.getCurrentInstance();
		
		//String outPath=(String) servletContext.getAttribute(Constants.OUT_FOLDER_SESSION_ATTRIBUTE);
		
		//String rootPath = servletContext.getRealPath("/");
		//rootPath+="/"+Constants.REPORTS_FOLDER+"/";
		
		//String outputUrl = (String) servletContext.getAttribute(Constants.SESSION_ID_ATTRIBUTE);
		//outputUrl=Constants.URL_REPORT_BASE_URL+"/"+outputUrl+"/"+report.getId()+ Constants.REPORT_EXT;
		String outputUrl=outURL+report.getId()+ Constants.REPORT_EXT;
		Date dts= new Date();
		outputUrl+="?"+dts.getTime();
		result.setUrl(outputUrl);
		
		String outputName = outPath+report.getId()+outSuffix+ Constants.REPORT_EXT;
		result.setPath(outputName);
		File outFile = new File(outputName);
		
		OutputStream outputStream=null;
		try {
			outputStream = new FileOutputStream(outFile);
		} catch (FileNotFoundException e) {
			result.assignError("Can't open: "+outFile);
		}
		
        String templateName=reportPath+report.getId()+ Constants.REPORT_EXT;
        String templateXml=reportPath+report.getId()+ Constants.XML_EXT;
		
		XlsReporter reporter = new XlsReporter();
		
        File inFile;
        InputStream inputStream;
        try {
            inFile = new File(templateName);
            inputStream = new FileInputStream(inFile);
        } catch (Exception e1) {
            XLSDocumentWriter documentWriter = new XLSDocumentWriter();
        	HSSFWorkbook outputTemplate = new HSSFWorkbook();
        	reporter.fillOutputWithException(outputTemplate, e1);
            documentWriter.writeDocument(outputTemplate, outputStream);
            e1.printStackTrace();
            result.assignError(e1.getMessage());
            return result;
        }

		InputStream xmlStream;
    	File xmlFile = new File(templateXml);
        //parse data xml
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try {
    		xmlStream = new FileInputStream(xmlFile);
            builder = factory.newDocumentBuilder();
            document = builder.parse(xmlStream);
        } catch (Exception e1) {
            XLSDocumentWriter documentWriter = new XLSDocumentWriter();
        	HSSFWorkbook outputTemplate = new HSSFWorkbook();
        	reporter.fillOutputWithException(outputTemplate, e1);
            documentWriter.writeDocument(outputTemplate, outputStream);
            e1.printStackTrace();
            result.assignError(e1.getMessage());
            return result;
        }
        try {
			xmlStream.close();
		} catch (IOException e2) {}
        
        Connection cnn=null;
        try {
        	cnn=ConnectionFactory.getConnection(source);
        	//fill ValueDistributor
        	ValueDistributorImpl vd = new ValueDistributorImpl(report.getParameters());
        	//build report
			reporter.process(inputStream, outputStream, document,cnn, vd); 
        } catch (Exception e1) {
            XLSDocumentWriter documentWriter = new XLSDocumentWriter();
        	HSSFWorkbook outputTemplate = new HSSFWorkbook();
        	reporter.fillOutputWithException(outputTemplate, e1);
            documentWriter.writeDocument(outputTemplate, outputStream);
            e1.printStackTrace();
            result.assignError(e1.getMessage());
            return result;
        }

        try {
			if(cnn!=null) cnn.close();
	        if(outputStream!=null) outputStream.close();
	        //inputStream.close();
		} catch (Exception e) {
		}
        
        return result;
        /*
		//4 debug
		String res="id:"+report.getId()+";";
		res+=" outPath:"+outPath;
		if(report.getParameters()!=null){
			res=res+" parms:"+report.getParameters().length+";";
			for (Parameter p: report.getParameters()){
				res=res+"\n"+"parm:"+p.getName()+":"+p.getValFrom()+"-"+p.getValTo();
			}
		}else{
			res=res+" parms:null;";
		}
		return res;
		*/
	}

}
