package org.xreport.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="parameter")
public class Parameter extends AbstractExternalizedBean{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private String id;

    @Column(name="src_type")
    private int src_type;
    
    @Column(name="name")
    private String name;

    @Column(name="is_list")
    private int listType;

    @Column(name="is_multi")
    private int multiSelect;

    @Column(name="list_sql")
    private String listSql;

    @Transient
    private boolean keepTime;

    @Transient
    private List<ListItemInt> itemsInt;

    @Transient
    private Date valFrom;

    @Transient
    private Date valTo;

    @Transient
    private Date valDate;

    @Transient
    private int valInt;

    @Transient
    private String valString;

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
	public Date getValFrom() {
		return valFrom;
	}
	public void setValFrom(Date valFrom) {
		this.valFrom = valFrom;
	}
	public Date getValTo() {
		return valTo;
	}
	public void setValTo(Date valTo) {
		this.valTo = valTo;
	}
	public Date getValDate() {
		return valDate;
	}
	public void setValDate(Date valDate) {
		this.valDate = valDate;
	}
	public int getValInt() {
		return valInt;
	}
	public void setValInt(int valInt) {
		this.valInt = valInt;
	}
	public String getValString() {
		return valString;
	}
	public void setValString(String valString) {
		this.valString = valString;
	}
	public String getListSql() {
		return listSql;
	}
	public void setListSql(String listSql) {
		this.listSql = listSql;
	}
	public boolean isKeepTime() {
		return keepTime;
	}
	public void setKeepTime(boolean keepTime) {
		this.keepTime = keepTime;
	}
	public int getListType() {
		return listType;
	}
	public void setListType(int listType) {
		this.listType = listType;
	}
	public int isMultiSelect() {
		return multiSelect;
	}
	public void setMultiSelect(int multiSelect) {
		this.multiSelect = multiSelect;
	}
	public List<ListItemInt> getItemsInt() {
		return itemsInt;
	}
	public void setItemsInt(List<ListItemInt> itemsInt) {
		this.itemsInt = itemsInt;
	}
	
}
