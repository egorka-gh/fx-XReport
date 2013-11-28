package org.xreport.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="trm_in_store")
public class UkmStore extends AbstractExternalizedBean {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="store_id")
    private int store_id;
    @Column(name="name")
    private String name;
    @Column(name="inn")
    private String inn;
    
    public int getStore_id() {
		return store_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInn() {
		return inn;
	}
	public void setInn(String inn) {
		this.inn = inn;
	}
}
