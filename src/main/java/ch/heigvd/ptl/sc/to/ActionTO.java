package ch.heigvd.ptl.sc.to;

import java.util.Date;

public class ActionTO implements ITO {
	private String id;
	
	private String type;

	private String user;
	
	private String issueId;
	
	private Date actionDate;
	
	private String reason;
	
	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public String getIssueId() {
		return issueId;
	}

	public String getReason() {
		return reason;
	}

	public String getUser() {
		return user;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
