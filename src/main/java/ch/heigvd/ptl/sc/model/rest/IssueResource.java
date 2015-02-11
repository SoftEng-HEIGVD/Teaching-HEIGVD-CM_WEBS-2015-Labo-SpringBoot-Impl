package ch.heigvd.ptl.sc.model.rest;

import ch.heigvd.ptl.sc.CityEngagementException;
import ch.heigvd.ptl.sc.rest.security.Roles;
import ch.heigvd.ptl.sc.model.persistence.IRepository;
import ch.heigvd.ptl.sc.model.persistence.IssueRepository;
import ch.heigvd.ptl.sc.model.rest.params.PagerAndSorterParams;
import ch.heigvd.ptl.sc.converter.IConverter;
import ch.heigvd.ptl.sc.converter.IssueConverter;
import ch.heigvd.ptl.sc.model.IModel;
import ch.heigvd.ptl.sc.model.Issue;
import ch.heigvd.ptl.sc.to.ITO;
import ch.heigvd.ptl.sc.to.IssueTO;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/issues")
public class IssueResource extends AbstractResource {
	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private IssueConverter issueConverter;

	@Override
	protected IConverter getConverter() {
		return issueConverter;
	}

	@Override
	protected IRepository getRepository() {
		return issueRepository;
	}

	@Roles
	@Override
	public Response findAll(PagerAndSorterParams pagerAndSorter) {
		return super.findAll(pagerAndSorter);
	}

	@Roles("citizen")
	@Override
	public Response create(ITO ito) {
		IssueTO issueTO = (IssueTO) ito;
		
		Issue issue = issueConverter.convertTargetToSource(issueTO);

		issue.setOwner(getCurrentUser());
		issue.setState("created");
		
		issue = issueRepository.enrichedSave(issue);
		
		return Response.ok(issueConverter.convertSourceToTarget(issue)).status(201).build();
	}

	@Override
	public Response update(String id, ITO ito) {
		IssueTO issueTO = (IssueTO) ito;
		
		Issue issue = issueRepository.findOne(id);
		
		if (issue == null) {
			throw new CityEngagementException(404, "Issue not found");
		}

		issueConverter.fillSourceFromTarget(issue, issueTO);
		
		issue = issueRepository.enrichedSave(issue);

		return Response.ok(issueConverter.convertSourceToTarget(issue)).build();
	}
}
