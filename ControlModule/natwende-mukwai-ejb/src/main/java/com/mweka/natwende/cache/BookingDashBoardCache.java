package com.mweka.natwende.cache;

//import java.util.Calendar;
import java.util.Date;
//import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.mweka.natwende.util.ServiceLocator;

@Singleton
@LocalBean
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class BookingDashBoardCache {
	private static final String TIMER_INFO = "TimerToPopulateDashboardWithDBData_RunsEvery5min";	
	private Logger log = Logger.getLogger(this.getClass());
	private Date lastUpdate;
	private boolean timerEnabled;
	
	@EJB
	private ServiceLocator serviceLocator;
	
	@Resource
	private EJBContext context;
	
	@Resource
	private TimerService timerService;

	
	@PostConstruct
	public void init() {
		//refDataCache = new ConcurrentHashMap<BookingListType, Object>();
		for(Object obj : timerService.getTimers()) {
	        Timer t = (Timer)obj;
	        if (t.getInfo() != null && t.getInfo().equals(TIMER_INFO)) {
	        	timerEnabled = true;
	        }
	    }
	}
	
	public void prepareDashboard() {
		DateTime dateTime = new DateTime().withTimeAtStartOfDay();
		//Calendar startDate = dateTime.toCalendar(Locale.ENGLISH);
		dateTime = dateTime.plusHours(24);
		//Calendar endDate = dateTime.toCalendar(Locale.ENGLISH);
		//loadBookingListForGivenTimeInterval(new BookingPeriodVO(startDate, endDate));
		lastUpdate = new Date();
	}	
	
	@Timeout
	public void executeTimer(Timer timer) {
		log.debug("Timer Service : " + timer.getInfo());
		log.debug("Current Time : " + new Date());
		log.debug("Next Timeout : " + timer.getNextTimeout());
		log.debug("Time Remaining : " + timer.getTimeRemaining());
		log.debug("____________________________________________");
		
		prepareDashboard();
	}
	
	public void cancelAllTimers() {
	    for(Object obj : timerService.getTimers()) {
	        Timer t = (Timer)obj;
	        t.cancel();
	    }
	    timerEnabled = false;
	}
	
	public void createTimer() {
		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setInfo(TIMER_INFO);
		timerConfig.setPersistent(true);
		ScheduleExpression schedule = new ScheduleExpression();
		schedule.timezone("Africa/Johannesburg").year("*").month("*").dayOfMonth("*").hour("*").minute("0/5");
		timerService.createCalendarTimer(schedule, timerConfig);
		timerEnabled = true;
	}

	public boolean isTimerEnabled() {
		return timerEnabled;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}	
}
