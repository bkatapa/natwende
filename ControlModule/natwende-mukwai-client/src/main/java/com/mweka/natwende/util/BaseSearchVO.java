package com.mweka.natwende.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class BaseSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlTransient
    @JsonIgnore
    private int startPosition = 0;
    
    @XmlTransient
    @JsonIgnore
    private int maxNumberOfResultsToRetrieve = 1000;
    
    @XmlTransient
    @JsonIgnore
    private boolean matchExact = false;
    
    @XmlTransient
    @JsonIgnore
    private String sortField;
    
    @XmlTransient
    @JsonIgnore
    private Boolean sortAscending = null;
    
    @XmlTransient
    @JsonIgnore
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
