package ch.heigvd.ptl.sc.persistence;

import ch.heigvd.ptl.sc.model.User;
import java.util.List;

public interface UserRepository extends IRepository<User> {
	public List<User> findByFirstname(String firstname);

	public List<User> findByLastname(String lastname);
}
