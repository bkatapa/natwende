package com.mweka.natwende.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.report.vo.ReportVO;
import com.mweka.natwende.util.SystemConfigurationBean;

@Stateless
@LocalBean
public class ReportFacade  {

	
	@EJB
	SystemConfigurationBean systemConfigurationBean;
	
	public void generateBookingVoucher(Integer bookingId, OutputStream os) throws Exception {
		
	}

	public void generateReport(ReportVO reportVO, ByteArrayOutputStream os) throws IOException, Exception {
		
	}
	

}
