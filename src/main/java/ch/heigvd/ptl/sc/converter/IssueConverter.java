package ch.heigvd.ptl.sc.converter;

import ch.heigvd.ptl.sc.model.Issue;
import ch.heigvd.ptl.sc.model.Comment;
import ch.heigvd.ptl.sc.model.persistence.IssueTypeRepository;
import ch.heigvd.ptl.sc.to.CommentTO;
import ch.heigvd.ptl.sc.to.IssueTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueConverter extends AbstractConverter<Issue, IssueTO> {
	@Autowired
	private CommentConverter commentConverter;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private IssueTypeConverter issueTypeConverter;
	
	@Autowired
	private IssueTypeRepository issueTypeRepository;
	
	@Override
	public Issue createEmptySource() {
		return new Issue();
	}

	@Override
	public IssueTO createEmptyTarget() {
		return new IssueTO();
	}

	@Override
	public void fillTargetFromSource(IssueTO target, Issue source) {
		target.setId(source.getId());
		target.setDescription(source.getDescription());
		
		target.setLat(source.getLat());
		target.setLng(source.getLng());

		target.setOwner(userConverter.convertSourceToTarget(source.getOwner()));
		target.setAssignee(userConverter.convertSourceToTarget(source.getAssignee()));
		
		target.setState(source.getState());
		
		target.setUpdatedOn(source.getUpdatedOn());
		
		target.setIssueType(issueTypeConverter.convertSourceToTarget(source.getIssueType()));

		List<CommentTO> temp = new ArrayList<>();
		for (Comment comment : source.getComments()) {
			temp.add(commentConverter.convertSourceToTarget(comment));
		}
		target.setComments(temp);
	}

	@Override
	public void fillSourceFromTarget(Issue source, IssueTO target) {
		source.setDescription(target.getDescription());
		
		if (source.getId() == null) {
			source.setIssueType(issueTypeRepository.findOne(target.getIssueType().getId()));
		}
		
		source.setLat(target.getLat());
		source.setLng(target.getLng());
	}
}
