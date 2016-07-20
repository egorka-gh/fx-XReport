package org.xreport.util;

public enum ParameterType {
	PPeriod("period"),
	PPeriodT("periodt"),
	PDate("pdate"),
	PStore("pstore");
	
	private ParameterType(String type) {
		this.type = type;
	}

    private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
