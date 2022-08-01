package com.mweka.natwende.resource.operator;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.mweka.natwende.helper.VelocityGen;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.resource.AbstractResourceBean;

@Named
@Path("/buses")
public class BusResource extends AbstractResourceBean<BusVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5658557552146568140L;

	@Inject
	private VelocityGen velocityGen;
	
	@GET
	@Path("busReg/{reg}/busFare/{fare}/templateScript")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getTemplateScript(@PathParam("reg") String busReg, @PathParam("fare") String busFare) {
		boolean error = false;
		String errorMsg = StringUtils.EMPTY;
		if (StringUtils.isBlank(busReg) || StringUtils.isBlank(busFare)) {
			error = true;
			errorMsg = "Parameter(s) missing. busReg [" + busReg + "], busFare [" + busFare + "]";
			log.debug(errorMsg);
		}
		if (!error) {			
			BusVO bus = serviceLocator.getBusDataFacade().getByReg(busReg);
			BigDecimal fareAmount = new BigDecimal(busFare);
			String templateScript = velocityGen.busTemplate(bus.getSeatsAsString(), fareAmount);
			
			if (StringUtils.isBlank(templateScript)) {
				error = true;
				errorMsg = "Error occurred while putting together a result. templateScript [" + templateScript + "]";
				log.debug(errorMsg);
			}
			if (!error) {
				return Response.status(Response.Status.OK)
						.entity(templateScript)
						.build();
			}
		}
		return Response.status(Response.Status.EXPECTATION_FAILED)
				.entity(errorMsg)
				.build();
	}
}
