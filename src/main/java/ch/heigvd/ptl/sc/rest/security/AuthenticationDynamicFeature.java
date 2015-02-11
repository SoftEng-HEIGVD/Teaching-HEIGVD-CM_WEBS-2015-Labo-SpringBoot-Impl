package ch.heigvd.ptl.sc.rest.security;

import ch.heigvd.ptl.sc.model.persistence.UserRepository;
import java.util.Arrays;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import org.springframework.beans.factory.annotation.Autowired;

@Provider
public class AuthenticationDynamicFeature implements DynamicFeature {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		Roles roles = resourceInfo.getResourceMethod().getAnnotation(Roles.class);

		if (roles != null) {
			if (roles.value().length == 1 && "any".equals(roles.value()[0])) {
				context.register(new AuthenticationFilter(userRepository, true));
			}
			else {
				context.register(new AuthenticationFilter(userRepository, Arrays.asList(roles.value())));
			}
		}
	}
}
