package ch.heigvd.ptl.sc;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfiguration {
  public @Bean MongoDbFactory mongoDbFactory() throws Exception {
    return new SimpleMongoDbFactory(new MongoClient(), "citizen-engagement-spring-boot");
  }
}

