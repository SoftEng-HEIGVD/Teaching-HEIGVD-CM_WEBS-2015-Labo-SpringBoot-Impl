package ch.heigvd.ptl.sc.model.rest;

import ch.heigvd.ptl.sc.model.persistence.UserRepository;
import ch.heigvd.ptl.sc.model.persistence.IRepository;
import ch.heigvd.ptl.sc.model.rest.params.PagerAndSorterParams;
import ch.heigvd.ptl.sc.rest.security.Roles;
import ch.heigvd.ptl.sc.converter.UserConverter;
import ch.heigvd.ptl.sc.converter.IConverter;
import ch.heigvd.ptl.sc.to.ITO;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/users")
public class UserResource extends AbstractResource {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserConverter userConverter;

	@Override
	protected IConverter getConverter() {
		return userConverter;
	}

	@Override
	protected IRepository getRepository() {
		return userRepository;
	}

	@Roles("staff")
	@Override
	public Response findAll(PagerAndSorterParams pagerAndSorter) {
		return super.findAll(pagerAndSorter);
	}
}
