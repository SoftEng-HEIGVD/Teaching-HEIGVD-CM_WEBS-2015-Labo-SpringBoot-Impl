package ch.heigvd.ptl.sc.model.persistence;

import ch.heigvd.ptl.sc.model.IssueType;
import java.util.List;

public interface IssueTypeRepository extends IRepository<IssueType> {
	public List<IssueType> findByName(String name);
}
