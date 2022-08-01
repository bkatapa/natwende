package com.mweka.natwende.helper;

import java.io.Serializable;

public class Config implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8200407738745605152L;
	private int curIndex, stretchLen;
	private boolean editTravelTime, done;
	
	public int getCurIndex() {
		return curIndex;
	}		
	public void setCurIndex(int curIndex) {
		this.curIndex = curIndex;
	}
	public int getStretchLen() {
		return stretchLen;
	}		
	public void setStretchLen(int stretchLen) {
		this.stretchLen = stretchLen;
	}
	public boolean isEditTravelTime() {
		return editTravelTime;
	}		
	public void setEditTravelTime(boolean editTravelTime) {
		this.editTravelTime = editTravelTime;
	}
	public boolean isDone() {
		return done;
	}		
	public void setDone(boolean done) {
		this.done = done;
	}
	
}
