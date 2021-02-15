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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.w3c.dom.Document;
import org.xreport.constants.Constants;
import org.xreport.entities.ListItemInt;
import org.xreport.entities.Parameter;
import org.xreport.entities.Report;
import org.xreport.entities.ReportResult;
import org.xreport.entities.ReportSchedule;
import org.xreport.entities.Source;
import org.xreport.entities.SourceType;
import org.xreport.entities.UkmStore;
import org.xreport.util.ConnectionFactory;
import org.xreport.util.FileUtil;
import org.xreport.util.ValueDistributorImpl;

import com.reporter.XlsReporter;
import com.reporter.document.XLSDocumentWriter;


@Service("xReportService")
public class XReportServiceImpl implements XReportService, ServletContextAware   { //, ApplicationContextAware

	private static ServletContext context;

	public void setServletContext(ServletContext servletContext) {
		XReportServiceImpl.context = servletContext;
	}

	/*
	private static ApplicationContext appCtx;

    public void setApplicationContext(ApplicationContext applicationContext) {
    	XReportServiceImpl.appCtx = applicationContext;
    }
    */

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
	
	@Override
	public List<ListItemInt> getListInt(String source, String sql) {
		final String sSql=sql;
		return new SqlClosure<List<ListItemInt>>(ConnectionFactory.getDataSource(source)) {
			public List<ListItemInt> execute(Connection connection) {
				try {
					PreparedStatement pstmt = connection.prepareStatement(sSql);
					return OrmElf.statementToList(pstmt, ListItemInt.class);
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
					/*
					String sql="SELECT * FROM xrep.report_shedule rs"+
								 " WHERE rs.active = 1"+
								   " AND rs.week_day IN (0, DAYOFWEEK(CURRENT_DATE()) + 1)"+
								   " AND rs.run_after_hour <= HOUR(NOW())"+
								   " AND (rs.last_run IS NULL OR rs.last_run < CURRENT_DATE())"+
								   " AND rs.send_to IS NOT NULL";
								   */
					String sql="SELECT * FROM vrun_shedule";
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
	
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext  appContext;
	
	@Override
	public ReportResult buildReport(final Report report, String source){
		//HttpGraniteContext ctx = (HttpGraniteContext)GraniteContext.getCurrentInstance();
		//appContext = XReportServiceImpl.appCtx;
		
		String reportPath = context.getRealPath("/");
		reportPath+="/"+Constants.REPORTS_FOLDER+"/";

		/*
		String outPath=(String) context.getAttribute(Constants.OUT_FOLDER_SESSION_ATTRIBUTE);
		String outURL = (String) context.getAttribute(Constants.SESSION_ID_ATTRIBUTE);
		outURL=Constants.URL_REPORT_BASE_URL+"/"+outURL+"/";
		*/
		String suffix=outSuffix(report);
		String outPath=context.getInitParameter(Constants.OUT_FOLDER_INIT_PARAMETER)+"/"+suffix+"/";
		String outURL=Constants.URL_REPORT_BASE_URL+"/"+suffix+"/";
		//clean output
		cleanupOutput();
		//create result folder
		new File(outPath).mkdirs();

		return buildInternal(report,source,"", reportPath, outPath, outURL);
	}
	
	protected String outSuffix(Report report){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		String uid = "";
		if (report!=null && report.getUserUID()!=null && !report.getUserUID().isEmpty()){
			uid=report.getUserUID();
		}
		if(uid.isEmpty() && context!=null){
			String session = (String) context.getAttribute(Constants.SESSION_ID_ATTRIBUTE);
			if(session!=null && !session.isEmpty()){
				uid=session;
			}
		}
		if(uid.isEmpty()){
			uid = UUID.randomUUID().toString();
		}
		return date+"/"+uid;
	}

	protected void cleanupOutput(){
		if(context==null) return;
		String outPath=context.getInitParameter(Constants.OUT_FOLDER_INIT_PARAMETER);
		if(outPath==null || outPath.isEmpty()) return;
		File dir = new File(outPath);
		if(!dir.exists()) return;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		File[] files = dir.listFiles();
		for (File f : files ){
			if(f.isDirectory() && !f.getName().equals(date)){
				try{
					FileUtil.RemoveDir(f.getAbsolutePath());
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
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
        	Parameter[] params = null;
        	if(report.getParameters() != null && !report.getParameters().isEmpty()) params= report.getParameters().toArray(new Parameter[0]);
        	ValueDistributorImpl vd = new ValueDistributorImpl(params);
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
