package com.mweka.natwende.report.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;

@Entity
@Table(name="report_parameters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReportParameters.findAll", query="SELECT rp FROM ReportParameters rp"),
    @NamedQuery(name = "ReportParameters.findByReportId", query ="SELECT rp FROM ReportParameters rp where rp.report.id = :reportId order by rp.displayOrder")
})
public class ReportParameters extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "report_id")
	private Report report;	
	
	@NotNull 
	private String paramkey;
	
	@NotNull 
	private String paramvalue;
	
	private String paramType;	
	private Integer displayOrder;
	
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public String getParamkey() {
		return paramkey;
	}
	public void setParamkey(String paramkey) {
		this.paramkey = paramkey;
	}
	public String getParamvalue() {
		return paramvalue;
	}
	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}
	
	public String getParamType() {
		return paramType;
	}
	
	public void setParamType(String value) {
		paramType = value;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	

}
