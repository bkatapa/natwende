package com.mweka.natwende.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

public class BaseSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlTransient
    private int startPosition = 0;
    
    @XmlTransient
    private int maxNumberOfResultsToRetrieve = 1000;
    
    @XmlTransient
    private boolean matchExact = false;
    
    @XmlTransient
    private String sortField;
    
    @XmlTransient
    private Boolean sortAscending = null;
    
    @XmlTransient
    private boolean forExport = false;

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getMaxNumberOfResultsToRetrieve() {
        return maxNumberOfResultsToRetrieve;
    }

    public void setMaxNumberOfResultsToRetrieve(int maxNumberOfResultsToRetrieve) {
        this.maxNumberOfResultsToRetrieve = maxNumberOfResultsToRetrieve;
    }

    public boolean isMatchExact() {
        return matchExact;
    }

    public void setMatchExact(boolean matchExact) {
        this.matchExact = matchExact;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Boolean getSortAscending() {
        return sortAscending;
    }

    public void setSortAscending(Boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    public boolean isForExport() {
        return forExport;
    }

    public void setForExport(boolean forExport) {
        this.forExport = forExport;
    }
}
