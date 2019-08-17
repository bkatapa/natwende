package com.mweka.natwende.payment.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.payment.vo.PaymentList;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.types.PaymentStatus;

@Stateless
@LocalBean
@Path("/")
public class PaymentFacade  extends AbstractFacade<PaymentVO> {
	
	public PaymentFacade() {
		super(PaymentVO.class);
	}
	
	public PaymentVO savePayment(PaymentVO payment) throws Exception {
		try {
			return serviceLocator.getPaymentDataFacade().update(payment);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public PaymentVO obtainByRef(PaymentVO payment) {
		return serviceLocator.getPaymentDataFacade().getByRef(payment.getRef());
	}
	
	@GET
	@Path("all/phone/{phoneNumber}/{paymentStatus}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getPaymentList(@PathParam("phoneNumber") String phoneNumber, @PathParam("paymentStatus") String paymentStatusString) {
		List<String> possibleValues = new ArrayList<>();
		for (PaymentStatus p : PaymentStatus.values()) {
			possibleValues.add(p.getDisplay().toLowerCase());
		}
		if (possibleValues.contains(paymentStatusString)) {
			PaymentStatus paymentStatus = PaymentStatus.valueOf(paymentStatusString.toUpperCase());
			List<PaymentVO> resultList = serviceLocator.getPaymentDataFacade().getPaymentListByPhoneAndStatus(phoneNumber, paymentStatus);
			return Response.status(Status.OK)
					.entity(new PaymentList(resultList))
					.build();
		}
		return  Response.status(Status.EXPECTATION_FAILED)
				.entity("Unexpected payment status" + paymentStatusString)
				.build();
	}
	
	@PUT
	@Path("one/phone")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response updatePaymentStatus(JAXBElement<PaymentVO> p) {
		PaymentVO payment = p.getValue();
		String successMsg = "Payment with ref [" + payment.getRef() + "] updated successfully.";
		try {
			serviceLocator.getPaymentDataFacade().update(payment);
			log.debug(successMsg);
		}
		catch (Exception ex) {
			log.debug(ex);
			return  Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpected outcome to payment status update: " + ex)
					.build();
		}
		return  Response.status(Status.OK)
				.entity(successMsg)
				.build();
	}
	
}
