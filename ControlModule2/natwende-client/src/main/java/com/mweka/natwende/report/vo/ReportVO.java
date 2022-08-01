package com.mweka.natwende.report.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.types.ReportOutputType;
import java.util.HashMap;

public class ReportVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	private String reportTitle;
	private String reportFile;

	private ReportOutputType outputType;

	HashMap<String, Object>	parameters = new HashMap<>();

	private OperatorVO operator;

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

	public ReportOutputType getOutputType() {
		return outputType;
	}

	public void setOutputType(ReportOutputType outputType) {
		this.outputType = outputType;
	}

	public HashMap<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, Object> parameters) {
		this.parameters = parameters;
	}

	public OperatorVO getOperator() {
		return operator;
	}

	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}	

}
