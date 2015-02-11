package ch.heigvd.ptl.sc.converter;

import ch.heigvd.ptl.sc.model.IssueType;
import ch.heigvd.ptl.sc.to.IssueTypeTO;
import org.springframework.stereotype.Service;

@Service
public class IssueTypeConverter extends AbstractConverter<IssueType, IssueTypeTO> {
	@Override
	public final IssueType createEmptySource() {
		return (IssueType) new IssueType();
	}

	@Override
	public final IssueTypeTO createEmptyTarget() {
		return (IssueTypeTO) new IssueTypeTO();
	}

	@Override
	public final void fillTargetFromSource(IssueTypeTO target, IssueType source) {
		target.setId(source.getId());
		target.setName(source.getName());
		target.setDescription(source.getName());
	}

	@Override
	public final void fillSourceFromTarget(IssueType source, IssueTypeTO target) {
		source.setName(target.getName());
		source.setDescription(target.getDescription());
	}
}
