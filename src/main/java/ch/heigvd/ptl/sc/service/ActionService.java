package ch.heigvd.ptl.sc.service;

import ch.heigvd.ptl.sc.CityEngagementException;
import ch.heigvd.ptl.sc.model.Action;
import ch.heigvd.ptl.sc.model.Comment;
import ch.heigvd.ptl.sc.model.Issue;
import ch.heigvd.ptl.sc.model.User;
import ch.heigvd.ptl.sc.persistence.ActionRepository;
import ch.heigvd.ptl.sc.persistence.IssueRepository;
import ch.heigvd.ptl.sc.persistence.UserRepository;
import ch.heigvd.ptl.sc.to.IssueActionTO;
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
	private ActionRepository actionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Issue processAction(User user, String issueId, IssueActionTO action) {
		Issue issue = findIssue(issueId);

		checkUserRights(user, issue, action.getType());
		
		try {
			switch (Action.ActionType.fromType(action.getType())) {
				case ADD_COMMENT:
					return comment(user, issue, action);
				case ADD_TAGS:
					return addTags(user, issue, action);
				case REMOVE_TAGS:
					return removeTags(user, issue, action);
				case REPLACE_TAGS:
					return replaceTags(user, issue, action);
				case ASSIGN:
					return assign(user, issue, action);
				case ACKNOWLEDGE:
					return ack(user, issue, action);
				case START:
					return start(user, issue, action);
				case REJECT:
					return reject(user, issue, action);
				case RESOLVE:
					return resolve(user, issue, action);
				default:
					throw new CityEngagementException(404, "Unknown action");
			}
		}
		catch (IllegalArgumentException iae) {
			throw new CityEngagementException(404, "Unknown action");
		}
	}
	
	private Issue comment(User user, Issue issue, IssueActionTO action) {
		addComment(user, issue, action.getPayload().getComment());

		addAction(Action.ActionType.ADD_COMMENT, user, issue, "Comment added.");
		
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue addTags(User user, Issue issue, IssueActionTO action) {
		for (String tag : action.getPayload().getTags()) {
			if (!issue.getTags().contains(tag)) {
				issue.getTags().add(tag);
			}
		}
		
		addAction(Action.ActionType.ADD_TAGS, user, issue, "Tags added to the issue.");
		
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue removeTags(User user, Issue issue, IssueActionTO action) {
		for (String tag : action.getPayload().getTags()) {
			if (issue.getTags().contains(tag)) {
				issue.getTags().remove(tag);
			}
		}
		
		addAction(Action.ActionType.REMOVE_TAGS, user, issue, "Tags removed from the issue.");
		
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue replaceTags(User user, Issue issue, IssueActionTO action) {
		issue.getTags().clear();
		issue.getTags().addAll(action.getPayload().getTags());
		
		addAction(Action.ActionType.REPLACE_TAGS, user, issue, "Tags replaced on the issue.");
		
		return issueRepository.enrichedSave(issue);
	}

	private Issue assign(User user, Issue issue, IssueActionTO action) {
		User assignee = userRepository.findOne(action.getPayload().getAssigneeId());
		
		if (user == null) {
			throw new CityEngagementException("Assignee not found.");
		}
		
		issue.setAssignee(assignee);
		changeState(Action.ActionType.ASSIGN, user, issue, "assigned", "The issue has been assigned.", action.getPayload().getComment());
		
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue ack(User user, Issue issue, IssueActionTO action) {
		changeState(Action.ActionType.ACKNOWLEDGE, user, issue, "acknowledged", "The staff has received the issue.", action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue start(User user, Issue issue, IssueActionTO action) {
		changeState(Action.ActionType.START, user, issue, "in_progress", "The issue is under investigation.", action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue reject(User user, Issue issue, IssueActionTO action) {
		changeState(Action.ActionType.REJECT,user, issue, "rejected", "It seems there is nothing to do there!", action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue resolve(User user, Issue issue, IssueActionTO action) {
		changeState(Action.ActionType.RESOLVE, user, issue, "resolved", "Yeah! Staff is proud to announce that the issue has been solved!", action.getPayload().getComment());
		return issueRepository.enrichedSave(issue);
	}
	
	private Issue changeState(Action.ActionType actionType, User user, Issue issue, String state, String mandatoryComment, String optionalComment) {
		issue.setState(state);
		
		addAction(actionType, user, issue, mandatoryComment);
		
		if (optionalComment != null && !"".equals(optionalComment)) {
			addComment(user, issue, optionalComment);
		}
		
		return issueRepository.enrichedSave(issue);
	}
	
	private void addAction(Action.ActionType actionType, User user, Issue issue, String reason) {		
		Action action = new Action();
		
		action.setActionType(actionType);
		action.setIssue(issue);
		action.setReason(reason);
		action.setUser(reason);
		
		issue.getActions().add(action);
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
