package ch.heigvd.ptl.sc;

import ch.heigvd.ptl.sc.to.ErrorTO;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CityEngagementExceptionMapper implements ExceptionMapper<CityEngagementException>{
	@Override
	public Response toResponse(CityEngagementException exception) {
		return Response.status(exception.getStatus()).entity(new ErrorTO(exception.getMessage())).build();
	}
}
