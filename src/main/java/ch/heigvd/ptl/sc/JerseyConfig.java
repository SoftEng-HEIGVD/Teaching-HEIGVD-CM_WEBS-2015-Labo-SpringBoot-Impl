package ch.heigvd.ptl.sc;

import ch.heigvd.ptl.sc.rest.ActionResource;
import ch.heigvd.ptl.sc.rest.IssueActionResource;
import ch.heigvd.ptl.sc.rest.security.AuthenticationDynamicFeature;
import ch.heigvd.ptl.sc.rest.UserResource;
import ch.heigvd.ptl.sc.rest.DataResource;
import ch.heigvd.ptl.sc.rest.IssueResource;
import ch.heigvd.ptl.sc.rest.IssueTypeResource;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(ObjectMapperProvider.class);
		register(AuthenticationDynamicFeature.class);
		register(CityEngagementExceptionMapper.class);
		register(DataResource.class);
		register(UserResource.class);
		register(IssueResource.class);
		register(IssueTypeResource.class);
		register(IssueActionResource.class);
		register(ActionResource.class);
	}
}
