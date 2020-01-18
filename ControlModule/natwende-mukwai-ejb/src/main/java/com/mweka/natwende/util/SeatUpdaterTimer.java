package com.mweka.natwende.util;

import java.util.Date;
import java.util.List;

//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
//import javax.ejb.Timeout;
import javax.ejb.Timer;
//import javax.ejb.TimerService;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import com.mweka.natwende.trip.search.vo.TempBookingSearchVO;
import com.mweka.natwende.trip.vo.TempBookingVO;
import com.mweka.natwende.types.SeatStatus;
 
@Singleton
@LocalBean
@Startup
public class SeatUpdaterTimer {
 
    //@Resource
    //private TimerService timerService;
    
    @EJB
    private ServiceLocator serviceLocator;
 /*
    @PostConstruct
    private void init() {
        timerService.createTimer(1000, 1000*60*60, "SeatUpdaterTimer_Info");
    }*/
 
    //@Timeout
    public void execute(Timer timer) {    	
        System.out.println("Timer Service : " + timer.getInfo());
        System.out.println("Current Time : " + new Date());
        System.out.println("Next Timeout : " + timer.getNextTimeout());
        System.out.println("Time Remaining : " + timer.getTimeRemaining());
        System.out.println("____________________________________________");
        
        TempBookingSearchVO searchVO = new TempBookingSearchVO();
        searchVO.setSeatStatus(SeatStatus.OCCUPIED);
        List<TempBookingVO> resultList = serviceLocator.getTempBookingDataFacade().findBySearchVO(searchVO);
        Date currentDate = new Date();
        
        for (TempBookingVO result : resultList) {
        	Date lastHeartBeat = result.getLastHeartBeat();
        	DateTime dt1 = new DateTime(lastHeartBeat);
    		DateTime dt2 = new DateTime(currentDate);
    		int minutes = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
        	if (minutes >= 5) {
				result.setWsSessionId(null);				
				result.setLastHeartBeat(new Date());				
				result.setSeatStatus(SeatStatus.VACANT);
				serviceLocator.getTempBookingDataFacade().update(result);
			}
        }
    }
 
}
