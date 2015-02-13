package ch.heigvd.ptl.sc.persistence;

import ch.heigvd.ptl.sc.model.Issue;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IssueRepositoryCustom {
	Issue enrichedSave(Issue issue);
}
