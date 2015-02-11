package ch.heigvd.ptl.sc.rest.security;

import ch.heigvd.ptl.sc.model.User;
import java.security.Principal;

public class CityEngagementSecurityContext implements javax.ws.rs.core.SecurityContext {
	private final CityEngagementPrincipal principal;

	public CityEngagementSecurityContext(User user) {
		this.principal = new CityEngagementPrincipal(user);
	}

	@Override
	public Principal getUserPrincipal() {
		return principal;
	}

	@Override
	public boolean isUserInRole(String role) {
		return principal.getUser().hasRole(role);
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public String getAuthenticationScheme() {
		return "";
	}

	public static class CityEngagementPrincipal implements Principal {
		private final User user;

		public CityEngagementPrincipal(User user) {
			this.user = user;
		}
		
		@Override
		public String getName() {
			return user.getFirstname() + " " + user.getLastname();
		}

		public User getUser() {
			return user;
		}
	}
}
