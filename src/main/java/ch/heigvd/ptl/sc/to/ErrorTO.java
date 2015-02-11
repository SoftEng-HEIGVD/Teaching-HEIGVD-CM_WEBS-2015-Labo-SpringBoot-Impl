package ch.heigvd.ptl.sc.to;

public class ErrorTO {
	private String message;

	public ErrorTO() {}

	public ErrorTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
