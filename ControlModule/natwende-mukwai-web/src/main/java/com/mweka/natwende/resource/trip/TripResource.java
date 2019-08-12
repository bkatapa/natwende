package com.mweka.natwende.resource.trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.mweka.natwende.resource.AbstractResourceBean;
import com.mweka.natwende.trip.search.vo.TripSearchResultVO;
import com.mweka.natwende.trip.search.vo.TripSearchVO;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.Town;

@Named
@Path("/trips")
public class TripResource extends AbstractResourceBean<TripVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4588251857595746399L;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

	@GET
	@Path("from/{fromTown}/to/{toTown}/date/{travelDate}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response scanForAndFetchTrips(@PathParam("fromTown") String from, @PathParam("toTown") String to, @PathParam("travelDate") String date) {
		boolean error = false;
		String errorMsg = StringUtils.EMPTY;
		if (StringUtils.isBlank(from) || StringUtils.isBlank(to) || StringUtils.isBlank(date)) {
			error = true;
			errorMsg = "Parameter(s) missing. fromTown [" + from + "], toTown [" + to + "], travelDate [" + date + "]";
			log.debug(errorMsg);
		}
		if (!error) {
			Town fromTown = Town.valueOf(from.toUpperCase());
			Town toTown = Town.valueOf(to.toUpperCase());
			Date travelDate = null;
			try {
				travelDate = SDF.parse(date);
			}
			catch (ParseException pex) {
				log.debug(pex);
				error = true;
				errorMsg = "Parameter(s) parsing-related error occurred. [" + pex.getMessage() + "]";				
			}
			if (!error) {
				TripSearchVO searchVO = new TripSearchVO(fromTown, toTown, travelDate);
				List<TripSearchResultVO> resultList = null; // implement this
				GenericEntity<List<TripSearchResultVO>> list = new GenericEntity<List<TripSearchResultVO>>(resultList) {};
				
				return Response.status(Response.Status.OK)
						.entity(list)
						.build();
			}
		}
		return Response.status(Response.Status.EXPECTATION_FAILED)
				.entity(errorMsg)
				.build();
	}
}
