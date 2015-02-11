package ch.heigvd.ptl.sc.service;

import ch.heigvd.ptl.sc.CityEngagementException;
import ch.heigvd.ptl.sc.model.Comment;
import ch.heigvd.ptl.sc.model.Issue;
import ch.heigvd.ptl.sc.model.User;
import ch.heigvd.ptl.sc.model.persistence.IssueRepository;
import ch.heigvd.ptl.sc.model.persistence.UserRepository;
import ch.heigvd.ptl.sc.to.ActionTO;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionService {
	private static final Map<String, Boolean> STAFF_ACTIONS;

	static {
		STAFF_ACTIONS = new HashMap<>();

		STAFF_ACTIONS.put("ack", Boolean.FALSE);
		STAFF_ACTIONS.put("assign", Boolean.FALSE);
		STAFF_ACTIONS.put("start", Boolean.TRUE);
		STAFF_ACTIONS.put("reject", Boolean.TRUE);
		STAFF_ACTIONS.put("resolve", Boolean.TRUE);
	}

	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Issue processAction(User user, String issueId, ActionTO action) {
		Issue issue = findIssue(issueId);

		checkUserRights(user, issue, action.getType());
		
		switch (action.getType()) {
			case "comment":
				return comment(user, issue, action);
			case "addTags":
				return addTags(user, issue, action);
			case "removeTags":
				return removeTags(user, issue, action);
			case "replaceTags":
				return replaceTags(user, issue, action);
			case "assign":
				return assign(user, issue, action);
			case "ack":
				return ack(user, issue, action);
			case "start":
				return start(user, issue, action);
			case "reject":
				return reject(user, issue, action);
			case "resolve":
				return resolve(user, issue, action);
			default:
				throw new CityEngagementException(404, "Unknown action");
		}
	}
	
	private Issue comment(User user, Issue issue, ActionTO action) {
		addComment(user, issue, action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue addTags(User user, Issue issue, ActionTO action) {
		for (String tag : action.getPayload().getTags()) {
			if (!issue.getTags().contains(tag)) {
				issue.getTags().add(tag);
			}
		}
		
		addComment(user, issue, "Tags added to the issue.");
		
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue removeTags(User user, Issue issue, ActionTO action) {
		for (String tag : action.getPayload().getTags()) {
			if (issue.getTags().contains(tag)) {
				issue.getTags().remove(tag);
			}
		}
		
		addComment(user, issue, "Tags removed from the issue.");
		
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue replaceTags(User user, Issue issue, ActionTO action) {
		issue.getTags().clear();
		issue.getTags().addAll(action.getPayload().getTags());
		
		addComment(user, issue, "Tags replaced on the issue.");
		
		return issueRepository.enrichedSave(issue);
	}

	private Issue assign(User user, Issue issue, ActionTO action) {
		User assignee = userRepository.findOne(action.getPayload().getAssigneeId());
		
		if (user == null) {
			throw new CityEngagementException("Assignee not found.");
		}
		
		issue.setAssignee(assignee);
		changeState(user, issue, "assigned", "The issue has been assigned.", action.getPayload().getComment());
		
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue ack(User user, Issue issue, ActionTO action) {
		changeState(user, issue, "acknowledged", "The staff has received the issue.", action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue start(User user, Issue issue, ActionTO action) {
		changeState(user, issue, "in_progress", "The issue is under investigation.", action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue reject(User user, Issue issue, ActionTO action) {
		changeState(user, issue, "rejected", "It seems there is nothing to do there!", action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue resolve(User user, Issue issue, ActionTO action) {
		changeState(user, issue, "resolved", "Yeah! Staff is proud to announce that the issue has been solved!", action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue changeState(User user, Issue issue, String state, String mandatoryComment, String optionalComment) {
		issue.setState(state);
		
		addComment(user, issue, mandatoryComment);
		
		if (optionalComment != null && !"".equals(optionalComment)) {
			addComment(user, issue, optionalComment);
		}
		
		return issueRepository.enrichedSave(issue);
	}
	
	private void addComment(User user, Issue issue, String text) {		
		Comment comment = new Comment();
			
		comment.setText(text);
		comment.setAuthor(user);
		comment.setPostedOn(Calendar.getInstance().getTime());
		
		issue.getComments().add(comment);
	}

	
	private void checkUserRights(User user, Issue issue, String actionType) {
		if (STAFF_ACTIONS.containsKey(actionType)) {
			if (user.hasRole("staff")) {
				if (STAFF_ACTIONS.get(actionType) && !issue.getAssignee().getId().equals(user.getId())) {
					throw new CityEngagementException(403, "You must be the assignee to perform the action.");
				}
			}
			else {
				throw new CityEngagementException(403);
			}
		}
	}
	
	private Issue findIssue(String issueId) {
		Issue issue = issueRepository.findOne(issueId);
		
		if (issue == null) {
			throw new CityEngagementException("Issue not found");
		}
		
		return issue;
	}
}
