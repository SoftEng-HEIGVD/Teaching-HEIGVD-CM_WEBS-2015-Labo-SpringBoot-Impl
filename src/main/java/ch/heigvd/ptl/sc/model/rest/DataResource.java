package ch.heigvd.ptl.sc.model.rest;

import ch.heigvd.ptl.sc.model.User;
import ch.heigvd.ptl.sc.model.Issue;
import ch.heigvd.ptl.sc.model.Comment;
import ch.heigvd.ptl.sc.model.IssueType;
import ch.heigvd.ptl.sc.model.persistence.UserRepository;
import ch.heigvd.ptl.sc.model.persistence.IssueRepository;
import ch.heigvd.ptl.sc.model.persistence.IssueTypeRepository;
import ch.heigvd.ptl.sc.converter.UserConverter;
import ch.heigvd.ptl.sc.converter.IssueConverter;
import ch.heigvd.ptl.sc.converter.IssueTypeConverter;
import ch.heigvd.ptl.sc.to.DumpTO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/data")
public class DataResource {
	private final Random rand = new Random(Calendar.getInstance().getTimeInMillis());
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("YYYY-MM-DD");
	
	private static final String[] DESCRIPTIONS_AND_COMMENTS = new String[] {
		"Morbi a odio cursus, finibus lorem ut, pellentesque elit.",
		"Nunc sollicitudin lorem at dolor placerat, eget ornare erat fringilla.",
		"Sed eget ipsum sit amet lacus dictum porttitor at facilisis velit.",
		"Integer at metus vitae erat porta pellentesque.",
		"Pellentesque iaculis ante vestibulum dolor finibus hendrerit.",
		"Mauris tempus orci quis orci lacinia cursus.",
		"Nam semper ligula quis nisl egestas, at pellentesque nunc tincidunt.",
		"Integer venenatis justo ac urna accumsan, eget hendrerit ligula eleifend.",
		"Ut sagittis ipsum sed nisl ultrices rutrum.",
		"Proin pretium lacus nec lectus congue, a finibus elit consequat.",
		"Sed id ligula semper, auctor metus et, mattis tortor.",
		"Aenean non massa quis urna pellentesque pellentesque in nec ex.",
		"Vestibulum non erat venenatis, finibus lorem ac, eleifend eros.",
		"Proin ac mi et turpis volutpat facilisis id eget est.",
		"Pellentesque mattis quam tincidunt sem rhoncus finibus."
	};
	
	private static final String[] TAGS = new String[] {
		"Proin", "Orci", "Egestas", "Lobortis", "Quam", "Non", "Posuere", "Lorem", "Etiam"
	};
	
	private static final String[] ISSUE_STATES = new String[] {
		"created", "acknowledged", "assigned", "in_progress", "solved", "rejected"
	};
	
	private static final String[] FIRSTNAMES = new String[] {
		"Alfred", "Henri", "Romain", "Benoit", "Alain", "Alex"
	};
	
	private static final String[] LASTNAMES = new String[] {
		"Dupont", "Dutoit", "Ducroc", "Desportes", "Terieur" 
	};
	
	private static final List<String[]> ROLES = new ArrayList<>(
		Arrays.asList(
			new String[] { "citizen" },
			new String[] { "staff" },
			new String[] { "citizen", "staff" }
		)
	);
	
	private static final float MIN_LAT = 46.766129f;
	private static final float MAX_LAT = 46.784234f;
	private static final float MIN_LNG = 6.622009f;
	private static final float MAX_LNG = 6.651878f;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IssueTypeRepository issueTypeRepository;

	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private IssueTypeConverter issueTypeConverter;
	
	@Autowired
	private IssueConverter issueConverter;
	
	private List<User> users = new ArrayList<>();
	private List<User> citizen = new ArrayList<>();
	private List<User> staff = new ArrayList<>();
	private List<IssueType> issueTypes = new ArrayList<>();
	private List<Issue> issues = new ArrayList();
	
	private float random (float low, float high) {
    return rand.nextFloat() * (high - low) + low;
	}

	private int randomInt (int low, int high) {
		return (int) Math.floor(rand.nextFloat() * (high - low) + low);
	}

	private Date randomDate(Date start, Date end) {
    return new Date(start.getTime() + (int) (rand.nextFloat() * (end.getTime() - start.getTime())));
	}

	private String[] generateRoles() {
		return ROLES.get(randomInt(0, ROLES.size()));
	}
	
	private List<String> generateTags() {
		List<String> tags = new ArrayList<>();
		
		for (int i = 0; i < randomInt(1, 10); i++) {
			String tag = TAGS[randomInt(0, TAGS.length)];
			if (!tags.contains(tag)) {
				tags.add(tag);
			}
		}

		return tags;
	}

	private List<Comment> generateComments(Date creationDate) throws ParseException {
		List<Comment> comments = new ArrayList<>();

		for (int i = 0; i < randomInt(1, 25); i++) {
			Comment c = new Comment();
			
			c.setText(DESCRIPTIONS_AND_COMMENTS[randomInt(0, DESCRIPTIONS_AND_COMMENTS.length)]);
			c.setPostedOn(randomDate(creationDate, SDF.parse("2015-06-01")));
			c.setAuthor(users.get(randomInt(0, users.size())));
			
			comments.add(c);
		}
		
		return comments;
	}

	private void populateIssues() throws ParseException {
		Date creationDate = randomDate(SDF.parse("2012-01-01"), SDF.parse("2015-06-01"));

		for (int i = 0; i < 100; i++) {
			Issue is = new Issue();
			
			is.setDescription(DESCRIPTIONS_AND_COMMENTS[randomInt(0, DESCRIPTIONS_AND_COMMENTS.length)]);
			is.setLat("" + random(MIN_LAT, MAX_LAT));
			is.setLng("" + random(MIN_LNG, MAX_LNG));
			is.setState(ISSUE_STATES[randomInt(0, ISSUE_STATES.length)]);
			is.setUpdatedOn(creationDate);
			is.setTags(generateTags());
			is.setComments(generateComments(creationDate));
			is.setIssueType(issueTypes.get(randomInt(0, issueTypes.size())));
			is.setOwner(citizen.get(randomInt(0, citizen.size())));
			is.setAssignee(staff.get(randomInt(0, staff.size())));
			
			issues.add(issueRepository.enrichedSave(is));
		}
	}
	
	private void populateIssueTypes() {
		IssueType t1 = new IssueType();
		
		t1.setName("broken streetlight"); 
		t1.setDescription("Light is broken");
		
		IssueType t2 = new IssueType();
		
		t2.setName("dangerous crossroad");
		t2.setDescription("Devil road");
		
		IssueType t3 = new IssueType();
		
		t3.setName("graffiti");
		t3.setDescription("Youngs are evil");

		issueTypes.add(issueTypeRepository.save(t1));
		issueTypes.add(issueTypeRepository.save(t2));
		issueTypes.add(issueTypeRepository.save(t3));
	}
	
	private void populateUsers() {
		for (int i = 0; i < 15; i++) {
			User u = new User();
			
			u.setFirstname(FIRSTNAMES[randomInt(0, FIRSTNAMES.length)]);
			u.setLastname(LASTNAMES[randomInt(0, LASTNAMES.length)]);
			u.setPhone("+" + randomInt(1000000, 10000000));
			u.setRoles(Arrays.asList(generateRoles()));
			
			u = userRepository.save(u);
			
			users.add(u);
			
			for (String role : u.getRoles()) {
				if (null != role) switch (role) {
					case "citizen":
						citizen.add(u);
						break;
					case "staff":
						staff.add(u);
						break;
				}
			}
		}
	}
	
	@Path("/populate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response populate() throws ParseException {
		userRepository.deleteAll();
		issueTypeRepository.deleteAll();
		issueRepository.deleteAll();

		populateIssueTypes();
		populateUsers();
		populateIssues();
		
		return Response.ok().build();
	}
	
//	@Path("/dump")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public DumpTO dump() {
//		return new DumpTO(
//			userConverter.convertSourceToTarget(userRepository.findAll()),
//			employeeConverter.convertSourceToTarget(employeeRepository.findAll()),
//			issueTypeConverter.convertSourceToTarget(issueTypeRepository.findAll()),
//			issueConverter.convertSourceToTarget(issueRepository.findAll())
//		);
//	}
}
