package org.xreport.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="report_shedule")
public class ReportSchedule extends AbstractExternalizedBean {
	private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private int id;

    @Column(name="source")
    private String source;

    @Column(name="report")
    private String report;

    @Column(name="active")
    private boolean active;
    
    @Column(name="week_day")
    private int week_day;

    @Column(name="run_after_hour")
    private int run_after_hour;

    @Column(name="last_run")
    private Date last_run;
    
    @Column(name="send_to")
    private String send_to;

    @Column(name="label")
    private String label;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getWeek_day() {
		return week_day;
	}

	public void setWeek_day(int week_day) {
		this.week_day = week_day;
	}

	public int getRun_after_hour() {
		return run_after_hour;
	}

	public void setRun_after_hour(int run_after_hour) {
		this.run_after_hour = run_after_hour;
	}

	public Date getLast_run() {
		return last_run;
	}

	public void setLast_run(Date last_run) {
		this.last_run = last_run;
	}

	public String getSend_to() {
		return send_to;
	}

	public void setSend_to(String send_to) {
		this.send_to = send_to;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
