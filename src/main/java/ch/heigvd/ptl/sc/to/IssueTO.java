package ch.heigvd.ptl.sc.to;

import java.util.Date;
import java.util.List;

public class IssueTO implements ITO {
	private String id;

	private UserTO owner;
	private UserTO assignee;
	
	private String description;
	
	private String lat;
	private String lng;
	
	private String state;
	
	private Date updatedOn;
	
	private IssueTypeTO issueType;
	
	private List<CommentTO> comments;

	private List<ActionTO> actions;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserTO getOwner() {
		return owner;
	}

	public void setOwner(UserTO owner) {
		this.owner = owner;
	}

	public UserTO getAssignee() {
		return assignee;
	}

	public void setAssignee(UserTO assignee) {
		this.assignee = assignee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public IssueTypeTO getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueTypeTO issueType) {
		this.issueType = issueType;
	}

	public List<CommentTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentTO> comments) {
		this.comments = comments;
	}

	public String getLat() {
		return lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public List<ActionTO> getActions() {
		return actions;
	}

	public void setActions(List<ActionTO> actions) {
		this.actions = actions;
	}
}
