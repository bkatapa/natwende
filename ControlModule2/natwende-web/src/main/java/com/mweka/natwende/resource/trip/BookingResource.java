package com.mweka.natwende.resource.trip;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mweka.natwende.resource.AbstractResourceBean;
import com.mweka.natwende.trip.vo.BookingVO;

@Named
@Path("/bookings")
public class BookingResource extends AbstractResourceBean<BookingVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6049119572285138958L;
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("create")
	public Response create(BookingVO vo) {
		return update(vo);
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("update")
	public Response update(BookingVO vo) {
		if (vo == null) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted booking parameter: " + vo)
					.build();
		}
		log.debug("Booking received: " + vo.getReservation().getReference());
		try {
			vo = serviceLocator.getBookingDataFacade().update(vo);
		}
		catch (Exception ex) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted booking persist outcome: " + ex)
					.build();
		}
		return Response.status(Status.OK)
				.entity(vo)
				.build(); 
	}
	
	@DELETE
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.TEXT_PLAIN})
	@Path("delete/{id}")
	public Response delete(@PathParam("id") Long id) {
		try {
			serviceLocator.getBookingDataFacade().deleteById(id);
		}
		catch (Exception ex) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted booking purge outcome: " + ex)
					.build();
		}
		return Response.status(Status.OK)
				.entity("booking successfully deleted.")
				.build();
	}
}
