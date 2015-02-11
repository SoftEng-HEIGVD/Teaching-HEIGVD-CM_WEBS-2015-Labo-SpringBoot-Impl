package ch.heigvd.ptl.sc.converter;

import ch.heigvd.ptl.sc.model.User;
import ch.heigvd.ptl.sc.to.UserTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserConverter extends AbstractConverter<User, UserTO> {
	@Override
	public User createEmptySource() {
		return new User();
	}

	@Override
	public UserTO createEmptyTarget() {
		return new UserTO();
	}

	@Override
	public void fillTargetFromSource(UserTO target, User source) {
		target.setId(source.getId());
		target.setFirstname(source.getFirstname());
		target.setLastname(source.getLastname());
		target.setPhone(source.getPhone());
		
		List<String> roles = new ArrayList<>();
		for (String role : source.getRoles()) {
			roles.add(role);
		}
		target.setRoles(roles);
	}

	@Override
	public void fillSourceFromTarget(User source, UserTO target) {
		source.setFirstname(target.getFirstname());
		source.setLastname(target.getLastname());
		source.setPhone(target.getPhone());

		List<String> roles = new ArrayList<>();
		for (String role : target.getRoles()) {
			roles.add(role);
		}
		source.setRoles(roles);
	}
}
