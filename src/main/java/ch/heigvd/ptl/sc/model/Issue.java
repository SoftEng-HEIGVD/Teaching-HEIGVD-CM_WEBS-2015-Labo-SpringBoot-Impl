package ch.heigvd.ptl.sc.model;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Issue implements IModel {
	@Id
	private String id;
	
	@DBRef
	private User owner;
	
	@DBRef
	private User assignee;
	
	private String description;
	
	private String lng;
	private String lat;
	
	private String state;
	
	private Date updatedOn;
	
	@DBRef
	private IssueType issueType;

	private List<Comment> comments; 
	
	private List<String> tags;
	
	public String getId() {
		return id;
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

	public User getOwner() {
		return owner;
	}

	public User getAssignee() {
		return assignee;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType type) {
		this.issueType = type;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
