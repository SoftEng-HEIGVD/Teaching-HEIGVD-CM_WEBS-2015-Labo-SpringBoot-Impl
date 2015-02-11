package ch.heigvd.ptl.sc.to;

import java.util.List;

public class DumpTO {
	private List<UserTO> users;
	private List<IssueTypeTO> issueTypes;
	private List<IssueTO> issues;

	public List<UserTO> getUsers() {
		return users;
	}

	public List<IssueTypeTO> getIssueTypes() {
		return issueTypes;
	}

	public List<IssueTO> getIssues() {
		return issues;
	}

	public void setUsers(List<UserTO> users) {
		this.users = users;
	}

	public void setIssueTypes(List<IssueTypeTO> issueTypes) {
		this.issueTypes = issueTypes;
	}

	public void setIssues(List<IssueTO> issues) {
		this.issues = issues;
	}
}
