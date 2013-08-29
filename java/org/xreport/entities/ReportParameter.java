package org.xreport.entities;



import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="report_params")
public class ReportParameter extends AbstractExternalizedBean {
    private static final long serialVersionUID = 1L;

    @Column(name="report")
    private String report;

    @Column(name="parameter")
    private String parameter;
    
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}

	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
    
}
