package com.mweka.natwende.resource.user;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import com.mweka.natwende.resource.AbstractResourceBean;
import com.mweka.natwende.user.vo.UserVO;

@Named
@Path("/users")
public class UserResource extends AbstractResourceBean<UserVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5855045274605911276L;

	@GET
	@Path("nrc/{nrc}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response getUser(@PathParam("nrc") String nrc/*, Boolean isMobile*/) {
		log.debug("Request received. Fetch user details with NRC [" + nrc + "]");
		if (StringUtils.isBlank(nrc)) {
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted user parameter: nrc [" + nrc + "]")
					.build();
		}
		try {
			UserVO vo = serviceLocator.getUserDataFacade().getUserByNrc(nrc);
			return Response.status(Status.OK)
					.entity(vo)
					.build();
		}
		catch (Exception ex) {
			log.debug(ex);
			return Response.status(Status.EXPECTATION_FAILED)
					.entity("Unexpexted result when fetching user details: " + ex)
					.build();
		}
	}
}
