package ch.heigvd.ptl.sc.to;

public class IssueActionTO {
	private String type;
	
	private PayloadTO payload;

	public PayloadTO getPayload() {
		return payload;
	}

	public String getType() {
		return type;
	}

	public void setPayload(PayloadTO payload) {
		this.payload = payload;
	}

	public void setType(String type) {
		this.type = type;
	}
}
