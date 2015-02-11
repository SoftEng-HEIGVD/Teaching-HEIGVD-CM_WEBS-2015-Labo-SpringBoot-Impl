package ch.heigvd.ptl.sc.model.rest;

import ch.heigvd.ptl.sc.model.persistence.IRepository;
import ch.heigvd.ptl.sc.model.persistence.IssueTypeRepository;
import ch.heigvd.ptl.sc.model.rest.params.PagerAndSorterParams;
import ch.heigvd.ptl.sc.rest.security.Roles;
import ch.heigvd.ptl.sc.converter.IConverter;
import ch.heigvd.ptl.sc.converter.IssueTypeConverter;
import ch.heigvd.ptl.sc.to.ITO;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/issueTypes")
public class IssueTypeResource extends AbstractResource {
	@Autowired
	private IssueTypeRepository issueTypeRepository;
	
	@Autowired
	private IssueTypeConverter issueTypeConverter;

	@Override
	protected IConverter getConverter() {
		return issueTypeConverter;
	}

	@Override
	protected IRepository getRepository() {
		return issueTypeRepository;
	}
}
