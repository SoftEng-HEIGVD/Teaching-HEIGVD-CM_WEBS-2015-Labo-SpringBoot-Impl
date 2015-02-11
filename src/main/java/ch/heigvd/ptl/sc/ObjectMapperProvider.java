package ch.heigvd.ptl.sc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
	@Override
	public ObjectMapper getContext(Class<?> type) {
		ObjectMapper om = new ObjectMapper();
		
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		return om;
	}
}