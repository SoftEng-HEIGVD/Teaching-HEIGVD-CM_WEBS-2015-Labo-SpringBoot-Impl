package ch.heigvd.ptl.sc.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Comment implements IModel {
	@Id
	private String id;
	
	private String text;
	
	private Date postedOn;
	
	@DBRef
	private User author;
	
	public String getDescription() {
		return text;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public String getId() {
		return id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User _author) {
		this.author = _author;
	}
}
