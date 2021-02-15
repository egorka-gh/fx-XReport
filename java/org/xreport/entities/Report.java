package org.xreport.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="report")
public class Report extends AbstractExternalizedBean {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private String id;

    @Column(name="src_type")
    private int src_type;
    
    @Column(name="name")
    private String name;
    
    @Column(name="engine")
    private int engine;
    
    @Transient
    private String userUID;

    @Transient
    private List<Parameter> parameters;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;

	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getSrc_type() {
		return src_type;
	}
	public void setSrc_type(int src_type) {
		this.src_type = src_type;
	}
	public String getUserUID() {
		return userUID;
	}
	public void setUserUID(String userUID) {
		this.userUID = userUID;
	}
	public int getEngine() {
		return engine;
	}
	public void setEngine(int engine) {
		this.engine = engine;
	}
	public List<Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	
}
