package ch.heigvd.ptl.sc.persistence;

import ch.heigvd.ptl.sc.model.Issue;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class IssueRepositoryImpl implements IssueRepositoryCustom {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Issue enrichedSave(Issue issue) {
		issue.setUpdatedOn(Calendar.getInstance().getTime());
		
		mongoTemplate.save(issue);
		
		return mongoTemplate.findById(issue.getId(), Issue.class);
	}
}
