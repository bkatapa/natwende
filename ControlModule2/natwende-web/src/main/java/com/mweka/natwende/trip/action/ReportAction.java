package com.mweka.natwende.trip.action;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.mweka.natwende.cdi.LoggedInUser;
import com.mweka.natwende.helper.NatwendeUtils;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.report.vo.ReportVO;
import com.mweka.natwende.types.PagePath;
import com.mweka.natwende.types.ReportOutputType;
import com.mweka.natwende.types.ReportType;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("ReportAction")
@SessionScoped
public class ReportAction implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private NatwendeUtils utils;

	@Inject
	@LoggedInUser
	private UserVO userVO;

	private ReportVO reportVO;
	private ReportType reportType;
	private Date startDate;
	private Date endDate;
	private Date startTime;
	private Date endTime;
	private boolean bookingsForToday;
	private boolean bookingsForTomorrow;
	private OperatorVO selectedOperator;
	
	private int dateRangeType = 1;
	
	private StreamedContent downloadFile;

	public ReportAction() {
	}

	@PostConstruct
	private void postConstruct() {
		reportVO = new ReportVO();
	}

	public void downloadReport() {
		
	}

	public void reset() {
//		tenantSearchVO.clearSearch();
//		selectedUserVO = null;
	}

	public String createReportVO(String reportType) {
		resetFormDates();
		reportVO = new ReportVO();
		switch (reportType) {
			case "ACTIVE_BOOKINGS":
				this.reportType = ReportType.ACTIVE_BOOKINGS;
				//this.dateRangeType = 1;
				break;
			case "CANCELLED_BOOKINGS":
				this.reportType = ReportType.CANCELLED_BOOKINGS;
				//this.dateRangeType = 1;
				break;
			case "PARKING_BOOKINGS":
				this.reportType = ReportType.PARKING_BOOKINGS;
				this.dateRangeType = 3;
				break;
		}

		return PagePath.GENERATE_REPORT_VIEW.getPath();
	}

	public ReportVO getReportVO() {
		return reportVO;
	}

	public void setReportVO(ReportVO reportVO) {
		this.reportVO = reportVO;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isBookingsForToday() {
		return bookingsForToday;
	}

	public void setBookingsForToday(boolean bookingsForToday) {
		this.bookingsForToday = bookingsForToday;
	}

	public boolean isBookingsForTomorrow() {
		return bookingsForTomorrow;
	}

	public void setBookingsForTomorrow(boolean bookingsForTomorrow) {
		this.bookingsForTomorrow = bookingsForTomorrow;
	}

	public OperatorVO getSelectedTenant() {
		return selectedOperator;
	}

	public void setSelectedOperator(OperatorVO selectedOperator) {
		this.selectedOperator = selectedOperator;
	}
	
	
	public StreamedContent getDownloadFile() {		
		try {
			// Do some basic date checks
			if (dateRangeType == 3) {
//				BookingDateValidationHelper.mergeBookingReportDatesAndTimes(startDate, startTime, endDate, endTime);			
//				BookingDateValidationHelper.ensureDatePrecedenceIntegrity(startDate, endDate, UserActionMode.REPORTS);
//				BookingDateValidationHelper.ensureTimePrecedenceIntegrity(startTime, endTime, UserActionMode.REPORTS);
			}
			
//			if (userVO.getOperatorVO() == null) {
//				throw new Exception("Sorry. You have not yet been assigned a parking site. Please get one assigned to you.");
//			}
		
			try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {			
				
				reportVO.setOutputType(ReportOutputType.EXCEL);
				reportVO.setReportFile(reportType.getReportFileName());
				
				HashMap<String, Object>	parameters = new HashMap<>();
//				parameters.put("OPERATOR_ID", userVO.getOperatorVO().getId());
//				parameters.put("OPERATOR_NAME", userVO.getOperatorVO().getName());
				if("".contains("")){
					parameters.put("OPERATOR_ID_CLAUSE", " booking.operator_id = "+userVO.getOperator().getId() + " ");
				} else if(selectedOperator != null){
					parameters.put("OPERATOR_ID_CLAUSE", " booking.operator_id = "+selectedOperator.getId() + " ");
				} else{
					parameters.put("OPERATOR_ID_CLAUSE", " 1=1 ");
				}
				Calendar date = Calendar.getInstance();
				date.setTime(new Date());
				
				switch (dateRangeType) {
					case 3:
						//Custom
						Calendar timeCal = Calendar.getInstance();
						timeCal.setTime(startTime);
						date.setTime(startDate);
						date.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
						date.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
						date.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
						date.set(Calendar.MILLISECOND, timeCal.get(Calendar.MILLISECOND));
						parameters.put("START_DATE", new Timestamp(date.getTimeInMillis()));

						timeCal.setTime(endTime);
						date.setTime(endDate);
						date.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
						date.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
						date.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
						date.set(Calendar.MILLISECOND, timeCal.get(Calendar.MILLISECOND));
						parameters.put("END_DATE", new Timestamp(date.getTimeInMillis()));
						break;
					case 2:
						//TOMORROW
						date.add(Calendar.HOUR_OF_DAY, 24);
					case 1:
					default:
						//TODAY
						date.set(Calendar.HOUR_OF_DAY, 0);
						date.set(Calendar.MINUTE, 0);
						date.set(Calendar.SECOND, 0);
						date.set(Calendar.MILLISECOND, 0);
//						startDate.setTime(date.getTime());
						parameters.put("START_DATE", new Timestamp(date.getTimeInMillis()));
						date.add(Calendar.HOUR_OF_DAY, 24);
//						endDate.setTime(date.getTime());
//						date.set(Calendar.HOUR_OF_DAY, 2);
//						date.set(Calendar.MINUTE, 0);
//						date.set(Calendar.SECOND, 0);
//						date.set(Calendar.MILLISECOND, 0);
						parameters.put("END_DATE", new Timestamp(date.getTimeInMillis()));
						break;
					
				}
				parameters.put("USERNAME", userVO.getUsername());
				
				reportVO.setParameters(parameters);
				

				serviceLocator.getReportFacade().generateReport(reportVO, os);
				
				downloadFile = DefaultStreamedContent.builder()
						.contentType("application/octect-stream")
						.name(reportType.getReportFileName().substring(0,reportType.getReportFileName().indexOf(".jrxml"))+".xls")
						.stream(() -> new java.io.ByteArrayInputStream(os.toByteArray()))
						.build();
				
				return downloadFile;
			}
			catch (Exception e) {
				throw e;
			}		
		} catch (Exception ex) {
			utils.getLog().error("Error downloading report", ex);
			utils.logErrorMessage(ex.getMessage());
		}
		return null;
	}	

	public int getDateRangeType() {
		return dateRangeType;
	}

	public void setDateRangeType(int dateRangeType) {
		this.dateRangeType = dateRangeType;
	}
	
	public ReportType getReportType(){
		return reportType;
	}	

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public void defaultEndDateToStartDate() {
		setEndDate(startDate);
		setEndTime(startTime);
	}
	
	public void resetFormDates() {
		setStartDate(null);
		setStartTime(null);
		setEndDate(null);
		setEndTime(null);
		dateRangeType = 1;
	}

}
