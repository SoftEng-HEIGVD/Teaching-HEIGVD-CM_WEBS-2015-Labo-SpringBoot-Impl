package ch.heigvd.ptl.sc.rest;

import ch.heigvd.ptl.sc.CityEngagementException;
import ch.heigvd.ptl.sc.converter.ActionConverter;
import ch.heigvd.ptl.sc.converter.IssueConverter;
import ch.heigvd.ptl.sc.model.User;
import ch.heigvd.ptl.sc.persistence.ActionRepository;
import ch.heigvd.ptl.sc.rest.security.CityEngagementSecurityContext;
import ch.heigvd.ptl.sc.rest.security.Roles;
import ch.heigvd.ptl.sc.service.ActionService;
import ch.heigvd.ptl.sc.to.IssueActionTO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/actions")
public class ActionResource {
	@Context
	protected SecurityContext securityContext;
	
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private ActionConverter actionConverter;
	
	@Roles("staff")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(
			actionConverter.convertSourceToTarget(actionRepository.findAll())
		).build();
	}

	private User getCurrentUser() {
		if (securityContext == null) {
			throw new CityEngagementException(500, "No security context found.");
		}
		
		CityEngagementSecurityContext.CityEngagementPrincipal principal =
			(CityEngagementSecurityContext.CityEngagementPrincipal) securityContext.getUserPrincipal();
		
		if (principal == null) {
			throw new CityEngagementException(500, "No principal found in the security context.");
		}
		
		if (principal.getUser() == null) {
			throw new CityEngagementException(500, "No user found in the security context principal.");
		}
		
		return principal.getUser();
	}
}
