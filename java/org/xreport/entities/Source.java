package org.xreport.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="source")
public class Source extends AbstractExternalizedBean {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private String id;

    @Column(name="type")
    private int type;
    
    @Column(name="name")
    private String name;
    
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
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
