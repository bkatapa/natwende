package com.mweka.natwende.report.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.types.ReportOutputType;
import com.mweka.natwende.types.ReportType;

@Entity
@Table(name = "report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query="SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findByParkingSiteIdAndReportType", query = "SELECT r FROM Report r where r.operator.id = :parkingSiteId and r.reportType = :reportType and r.status = :status ")
})
public class Report extends BaseEntity {	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private Operator operator;
	
	@NotNull
    private String reportTitle;
	
    @NotNull
    private String reportFile;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private ReportType reportType;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ReportOutputType outputType;
    
    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private List<ReportParameters> reportParameters;
   
    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportFile() {
        return reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }
    
	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public List<ReportParameters> getReportParameters() {
		return reportParameters;
	}

	public void setReportParameters(List<ReportParameters> reportParameters) {
		this.reportParameters = reportParameters;
	}

	public ReportOutputType getOutputType() {
		return outputType;
	}

	public void setOutptType(ReportOutputType outputType) {
		this.setOutputType(outputType);
	}

	public void setOutputType(ReportOutputType outputType) {
		this.outputType = outputType;
	}

	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}
	
}
