package ch.heigvd.ptl.sc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {
	@Value("${mongo.env}")
	private String env;
	
	@Value("${mongo.db}")
	private String dbName;
	
	@Value("${mongo.user}")
	private String dbUser;
	
	@Value("${mongo.password}")
	private String dbPassword;

	@Value("${mongo.port}")
	private int port;
	
	@Value("${mongo.host}")
	private String host;
	
	public String getEnv() {
		return env;
	}

	public String getDbName() {
		return dbName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
}