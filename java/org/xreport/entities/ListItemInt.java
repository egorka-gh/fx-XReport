package org.xreport.entities;

import javax.persistence.Column;
import javax.persistence.Id;

public class ListItemInt extends AbstractExternalizedBean{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private int id;
    @Column(name="label")
    private String label;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
