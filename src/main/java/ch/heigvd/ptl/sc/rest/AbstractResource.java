package ch.heigvd.ptl.sc.rest;

import ch.heigvd.ptl.sc.CityEngagementException;
import ch.heigvd.ptl.sc.model.IModel;
import ch.heigvd.ptl.sc.persistence.IRepository;
import ch.heigvd.ptl.sc.rest.params.PagerAndSorterParams;
import ch.heigvd.ptl.sc.rest.security.Roles;
import ch.heigvd.ptl.sc.converter.IConverter;
import ch.heigvd.ptl.sc.model.User;
import ch.heigvd.ptl.sc.rest.security.CityEngagementSecurityContext;
import ch.heigvd.ptl.sc.to.ITO;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class AbstractResource {
	@Context
	protected SecurityContext securityContext;
	
	protected User getCurrentUser() {
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findAll(@BeanParam PagerAndSorterParams pagerAndSorter) {
		Sort sort = new Sort(pagerAndSorter.getSort());
		Pageable pageable = new PageRequest(pagerAndSorter.getPage(), pagerAndSorter.getSize(), sort);
		return Response.ok(getConverter().convertSourceToTarget(getRepository().findAll(pageable).getContent())).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(ITO ito) {
		IModel model = getRepository().save(getConverter().convertTargetToSource(ito));
		
		return Response.ok(getConverter().convertSourceToTarget(model)).status(201).build();
	}
	
	@Roles
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@PathParam("id") String id) {
		IModel model = getRepository().findOne(id);
		
		if (model == null) {
			throw new CityEngagementException(404, "Model not found.");
		}
		
		return Response.ok(getConverter().convertSourceToTarget(model)).build();
	}
	
	@Roles("staff")
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id, ITO ito) {
		IModel model = getRepository().findOne(id);

		if (model == null) {
			throw new CityEngagementException(404, "Model not found.");
		}
		
		getConverter().fillSourceFromTarget(model, ito);
		
		IModel result = getRepository().save(model);

		return Response.ok(getConverter().convertSourceToTarget(result)).build();
	}

	@Roles("staff")
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		getRepository().delete(id);
		return Response.ok().status(204).build();
	}

	protected abstract IConverter<IModel, ITO> getConverter();
	
	protected abstract IRepository<IModel> getRepository();
}
