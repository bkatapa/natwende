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
import com.mweka.natwende.trip.vo.ReservationVO;

@Named
@Path("/reservations")
public class ReservationResource extends AbstractResourceBean<ReservationVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3609471663949493904L;
	
	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("create")
	public Response create(ReservationVO vo) {
		return update(vo);
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Path("update")
	public Response update(ReservationVO vo) {
		if (vo == null) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted reservation parameter: " + vo)
					.build();
		}
		log.debug("Reservation received: " + vo.getReference());
		try {
			vo = serviceLocator.getReservationDataFacade().update(vo);
		}
		catch (Exception ex) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted reservation persist outcome. Reference [" + vo.getReference() + "]\n" + ex)
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
			serviceLocator.getReservationDataFacade().deleteById(id);
		}
		catch (Exception ex) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted reservation purge outcome. [id=" + id + "],\n" + ex)
					.build();
		}
		return Response.status(Status.OK)
				.entity("Reservation successfully deleted. [id=" + id + "]")
				.build();
	}

}
