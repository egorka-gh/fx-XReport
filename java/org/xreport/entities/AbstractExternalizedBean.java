package org.xreport.entities;

import java.io.Serializable;

public abstract class AbstractExternalizedBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int persistState=1; //0-new, -1 - changed, 1-persisted  

	public int getPersistState() {
		return persistState;
	}
	public void setPersistState(int persistState) {
		this.persistState = persistState;
	}
}
