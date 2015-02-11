package ch.heigvd.ptl.sc.to;

import java.util.Date;

public class CommentTO implements ITO {
	private String id;
	
	private String text;
	
	private Date postedOn;

	private UserTO author;
	
	public String getText() {
		return text;
	}

	public String getId() {
		return id;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public UserTO getAuthor() {
		return author;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	public void setAuthor(UserTO author) {
		this.author = author;
	}
}
