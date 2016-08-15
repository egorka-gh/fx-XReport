package org.xreport.entities;

public class ReportResult extends AbstractExternalizedBean {
    private static final long serialVersionUID = 1L;

    private String id;
    private boolean hasError;
    private String url;
    private String path;
    private String error;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public void assignError(String error) {
		hasError=true;
		this.error = error;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}
