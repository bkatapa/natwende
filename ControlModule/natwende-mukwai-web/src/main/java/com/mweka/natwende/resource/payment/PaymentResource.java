package com.mweka.natwende.resource.payment;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

import com.mweka.natwende.payment.vo.PaymentEvent;
import com.mweka.natwende.payment.vo.PaymentList;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.resource.AbstractResourceBean;
import com.mweka.natwende.trip.vo.ReservationVO;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.PaymentStatus;

@Named
@Path("/payments")
public class PaymentResource extends AbstractResourceBean<PaymentVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3268777483232446445L;
	
	@Inject
	private Event<PaymentEvent> paymentEvent;

	@GET
	@Path("phone/{customerPhone}/{paymentStatus}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getEntityList(@PathParam("customerPhone") String customerPhone,	@PathParam("paymentStatus") String paymentStatus) {
		List<String> possibleValues = new ArrayList<>();
		for (PaymentStatus p : PaymentStatus.values()) {
			possibleValues.add(p.getDisplay().toLowerCase());
		}
		if (!possibleValues.contains(paymentStatus.toLowerCase())) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpected paymentStatus parameter: " + paymentStatus)
					.build();
		}
		PaymentStatus pStatus = PaymentStatus.valueOf(paymentStatus.toUpperCase());
		List<PaymentVO> resultList = serviceLocator.getPaymentDataFacade().getPaymentListByPhoneAndStatus(customerPhone, pStatus);
		return Response.status(Status.OK)
				.entity(new PaymentList(resultList))
				.build();
	}
	
	@POST
	@Path("create")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response create(PaymentVO vo) {
		if (vo == null) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted payment parameter: " + vo)
					.build();
		}
		log.debug("Payment received for persisting to database: " + vo.getRef());
		try {
			vo = serviceLocator.getPaymentDataFacade().update(vo);
		}
		catch (Exception ex) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted payment persist outcome: " + ex)
					.build();
		}
		return Response.status(Status.OK)
				.entity(vo)
				.build(); 
	}
	
	@PUT
	@Path("ref")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response updatePaymentStatus(JAXBElement<PaymentVO> p) {
		PaymentVO payment = p.getValue();
		try {
			PaymentVO result = serviceLocator.getPaymentDataFacade().getByRef(payment.getRef());
			result.setPaymentStatus(payment.getPaymentStatus());
			payment = serviceLocator.getPaymentDataFacade().update(result);
			ReservationVO reservation = serviceLocator.getReservationDataFacade().getByPaymentRef(payment.getRef());
			
			switch (payment.getPaymentStatus()) {
			case AUTHORIZED : reservation.setBookingStatus(BookingStatus.CONFIRMED);
			break;
			case DECLINED : reservation.setBookingStatus(BookingStatus.FAILED);
			break;
			default : reservation.setBookingStatus(BookingStatus.UNKNOWN);
			}
			serviceLocator.getReservationDataFacade().update(reservation);
			paymentEvent.fire(new PaymentEvent(payment));
			log.debug("Payment ref [" + payment.getRef() + "] updated successfully.");
		}
		catch (Exception ex) {
			log.debug(ex);
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpected outcome on paymentStatus update: " + ex.getMessage())
					.build(); 
		}
		return Response.status(Status.OK)
				.entity("Payment with ref [" + payment.getRef() + "] updated successfully.")
				.build();
	}
	
	@DELETE
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
	@Path("delete/{id}")
	public Response delete(@PathParam("id") Long id) {
		try {
			serviceLocator.getPaymentDataFacade().deleteById(id);
		}
		catch (Exception ex) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted payment purge outcome. [id=" + id + "],\n" + ex)
					.build();
		}
		return Response.status(Status.OK)
				.entity("Payment successfully deleted. [id=" + id + "]")
				.build();
	}
}
