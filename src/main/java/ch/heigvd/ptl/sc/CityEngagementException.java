package ch.heigvd.ptl.sc;

public class CityEngagementException extends RuntimeException {
	private int status = 500;
	
	public CityEngagementException() {}

	public CityEngagementException(String message) {
		super(message);
	}

	public CityEngagementException(Throwable cause) {
		super(cause);
	}

	public CityEngagementException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CityEngagementException(int status) {}

	public CityEngagementException(int status, String message) {
		super(message);
	}

	public CityEngagementException(int status, Throwable cause) {
		super(cause);
	}

	public CityEngagementException(int status, String message, Throwable cause) {
		super(message, cause);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
