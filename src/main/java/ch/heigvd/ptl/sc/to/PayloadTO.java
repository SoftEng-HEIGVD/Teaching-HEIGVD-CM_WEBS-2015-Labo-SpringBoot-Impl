package ch.heigvd.ptl.sc.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PayloadTO {
	private String comment;

	private String assigneeId;
	
	private List<String> tags;
	
	@JsonCreator
	public static PayloadTO of(
		@JsonProperty("comment") final String comment,
		@JsonProperty("text") final String text
	) {
		return of(comment != null ? comment : text);
	}
	
	public static PayloadTO of(
		@JsonProperty("comment") final String comment) {
		return new PayloadTO(comment);
	}

	public PayloadTO() {
	}

	public PayloadTO(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}
}
