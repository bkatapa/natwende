package com.mweka.natwende.dashboard.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

//import org.joda.time.DateTime;
import org.primefaces.model.chart.ChartModel;

import com.mweka.natwende.helper.NatwendeUtils;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.util.ServiceLocator;

import javax.enterprise.context.SessionScoped;

@Named("BookingDashboardAction")
@SessionScoped
public class BookingDashboardAction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OperatorVO selectedOperator;
	//private boolean filterOnTenants = true;
	private final DataModelGenerator modelGenerator;
	private Long operatorId;	
	
	@Inject
	private ServiceLocator serviceLocator;
	
	@Inject
	private NatwendeUtils utils;
	
	public BookingDashboardAction() {
		modelGenerator = new DataModelGenerator();
	}
	
	@PostConstruct
	public void initDashboard() {	
	}
	
	public ChartModel getBookedVsAvailablePieChartModel() {
		long allocatedBays = 0L, bookedBayCount = 0L;		
        return modelGenerator.createPieChartModel(allocatedBays, bookedBayCount);
    }
	
	public ChartModel getHourlyBookingsPerTenantChartModel() {	
		Map<Integer, Integer> modelDataMap = new TreeMap<Integer, Integer>();
		operatorId = selectedOperator == null ? null : selectedOperator.getId();
		modelDataMap.putAll(serviceLocator.getBookingDashboardFacade().fetchHourlyBookingCountMapPerTenant(operatorId));
		return modelGenerator.createHourlyBookingModel(utils.getOperatorNameForCurrentUser(), modelDataMap);
	}
	
	public void generate() {				
	}
	
	public List<BookingVO> getBookingVOList(){
		return null;
	}
	
	public List<OperatorVO> getAvailableOperators() {
		return null;		
	}

	public OperatorVO getSelectedOperator() {
		return selectedOperator;
	}

	public void setSelectedOperator(OperatorVO selectedOperator) {
		this.selectedOperator = selectedOperator;
	}
	
	public void activateTimer() {
		boolean result = serviceLocator.getBookingDashboardFacade().enableDashboardTimer();
		if (result) {
			//utils.logInfoMessage("Timer activated successfully!");
		}
	}
	
	public void disableTimers() {
		boolean result = serviceLocator.getBookingDashboardFacade().disableDashboardTimers();
		if (!result) {
			utils.showWarnMessageDlg("Timer disabled. The dashboard may not reflect the correct status of bookings");
		}
	}
	
	public boolean isTimerActive() {
		return serviceLocator.getBookingDashboardFacade().isTimerEnabled();
	}
	
	public String getLastUpdate() {
		Date lastUpdate = serviceLocator.getBookingDashboardFacade().getLastUpdate();
		return lastUpdate == null ? "" : new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(lastUpdate);
	}
	
	// Refreshes UI dash-board after fetching data from booking cache
	public void onTimeout(){
		if (isTimerActive()) {
			//serviceLocator.getBookingDashboardFacade().fetchLatestSnapshotOfBookingDashboard(operatorId, true);			
		}
		else {
			utils.logWarnMessage("Auto update time is disabled. Click Refresh to refresh the dashboard");
		}
	}
}
