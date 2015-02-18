package ch.heigvd.ptl.sc.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Action implements IModel {
	public static enum ActionType {
		ADD_COMMENT("addComment"),
		ADD_TAGS("addTags"),
		REMOVE_TAGS("removeTags"),
		REPLACE_TAGS("replaceTags"),
		ASSIGN("assign"),
		ACKNOWLEDGE("ack"),
		START("start"),
		REJECT("reject"),
		RESOLVE("resolve");
		
		private final String type;

		private ActionType(String type) {
			this.type = type;
		}
		
		public String getType() {
			return type;
		}
		
		public static ActionType fromType(String type) {
			for (ActionType at : ActionType.values()) {
				if (at.type.equalsIgnoreCase(type)) {
					return at;
				}
			}
			
			throw new IllegalArgumentException("Type " + type + " not supported.");
		}
	}
	
	@Id
	private String id;

	private String user;
	
	private Date actionDate;
	
	private ActionType actionType;
	
	private String reason;

	@DBRef
	private Issue issue;
		
	public String getId() {
		return id;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public String getReason() {
		return reason;
	}

	public String getUser() {
		return user;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
}
