package ch.heigvd.ptl.sc.rest;

import ch.heigvd.ptl.sc.converter.ActionConverter;
import ch.heigvd.ptl.sc.persistence.ActionRepository;
import ch.heigvd.ptl.sc.rest.params.PagerAndSorterParams;
import ch.heigvd.ptl.sc.rest.security.Roles;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
	public Response findAll(@BeanParam PagerAndSorterParams pagerAndSorter) {
		Sort sort = new Sort(pagerAndSorter.getSort());
		Pageable pageable = new PageRequest(pagerAndSorter.getPage(), pagerAndSorter.getSize(), sort);
		return Response.ok(actionConverter.convertSourceToTarget(actionRepository.findAll(pageable).getContent())).build();
	}
}
