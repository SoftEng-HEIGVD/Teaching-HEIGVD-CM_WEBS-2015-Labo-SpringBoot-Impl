package ch.heigvd.ptl.sc.rest.security;

import ch.heigvd.ptl.sc.model.User;
import ch.heigvd.ptl.sc.persistence.UserRepository;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

public class AuthenticationFilter implements ContainerRequestFilter {
	private final UserRepository userRepository;
	
	private List<String> requiredRoles; 
	
	private boolean any = false;

	public AuthenticationFilter(UserRepository userRepository, boolean any) {
		this.userRepository = userRepository;
		this.any = any;
	}
	
	public AuthenticationFilter(UserRepository userRepository, List<String> roles) {
		this.userRepository = userRepository;
		requiredRoles = roles;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String userId = requestContext.getHeaderString("x-user-id");
		
		if (userId != null && !"".equals(userId)) {
			User user = userRepository.findOne(userId);
			
			if (user != null) {
				CityEngagementSecurityContext sc = new CityEngagementSecurityContext(user);
				
				requestContext.setSecurityContext(sc);
				
				if (!any) {
					for (String r : requiredRoles) {
						if (sc.isUserInRole(r)) {
							return;
						}
					}
					
					requestContext.abortWith(Response.serverError().status(403).build());
				}
			}
			else {
				requestContext.abortWith(Response.serverError().status(401).build());
			}
		}
		else {
			requestContext.abortWith(Response.serverError().status(401).build());
		}
	}
}
