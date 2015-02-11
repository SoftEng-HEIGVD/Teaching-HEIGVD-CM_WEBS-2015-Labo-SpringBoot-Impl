package ch.heigvd.ptl.sc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class CityEngagementApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CityEngagementApplication.class);
	}

	public static void main(String[] args) {
		new CityEngagementApplication().configure(new SpringApplicationBuilder(CityEngagementApplication.class)).run(args);
	}
}
