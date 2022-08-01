package com.mweka.natwende.dashboard.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.collections.CollectionUtils;
import com.mweka.natwende.cache.BookingDashBoardCache;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.util.ServiceLocator;
import java.util.Calendar;
import java.util.Date;

@Stateless
public class BookingDashboardFacade {
	
	@EJB
	private BookingDashBoardCache dashboardCache;
	
	@EJB
	private ServiceLocator serviceLocator;	
	
	public void refreshDashboard() {		
		dashboardCache.prepareDashboard();
	}
	
	public boolean disableDashboardTimers() {
		dashboardCache.cancelAllTimers();
		return dashboardCache.isTimerEnabled();
	}
	
	public boolean enableDashboardTimer() {
		dashboardCache.createTimer();
		return dashboardCache.isTimerEnabled();
	}
	
	public boolean isTimerEnabled() {
		return dashboardCache.isTimerEnabled();
	}
	
	
	
	public Map<Integer, Integer> fetchHourlyBookingCountMapPerTenant(final Long tenantId) {
		List<BookingVO> bookingList = new ArrayList<>();
		Map<Integer, Integer> hourlyBookingsPerTenantMap = new HashMap<>();

		for (int count = 0; count < 24; count++) {
			hourlyBookingsPerTenantMap.put(count,0);
		}
		
		if (CollectionUtils.isNotEmpty(bookingList)) {

			for (BookingVO bookingVO : bookingList) {
				
				Calendar c = Calendar.getInstance();
				c.setTime(bookingVO.getUpdateDate());
				int startHour = c.get(Calendar.HOUR_OF_DAY);
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				c.set(Calendar.MILLISECOND, 999);
				Calendar end = Calendar.getInstance();
				end.setTime(bookingVO.getUpdateDate());
				int endHour = 23;
				if (c.getTimeInMillis() >= bookingVO.getUpdateDate().getTime()) {
					endHour = end.get(Calendar.HOUR_OF_DAY);
				}
				for (int count = 0; count < 24; count++) {
					if (count >= startHour && count <= endHour) {
						hourlyBookingsPerTenantMap.put(count, hourlyBookingsPerTenantMap.get(count) + 1);
					}
				}
			}

		}
		return hourlyBookingsPerTenantMap;
	}
	
	public List<BookingVO> getBookingVOList(long tenantId){
		return null;
	}
	
	public Date getLastUpdate() {
		return dashboardCache.getLastUpdate();
	}
}
