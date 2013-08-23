package org.xreport.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.sansorm.OrmElf;
import org.sansorm.SqlClosure;
import org.springframework.stereotype.Service;
import org.xreport.entities.SourceType;

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
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}.execute();		
	}

}
