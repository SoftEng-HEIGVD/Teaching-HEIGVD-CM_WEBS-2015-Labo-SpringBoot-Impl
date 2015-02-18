package ch.heigvd.ptl.sc.converter;

import ch.heigvd.ptl.sc.model.Action;
import ch.heigvd.ptl.sc.to.ActionTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ActionConverter extends AbstractConverter<Action, ActionTO> {
	@Override
	public final Action createEmptySource() {
		return (Action) new Action();
	}

	@Override
	public final ActionTO createEmptyTarget() {
		return (ActionTO) new ActionTO();
	}

	@Override
	public final void fillTargetFromSource(ActionTO target, Action source) {
		fillTargetFromSourceForIssue(target, source);
		target.setIssueId(source.getIssue().getId());
	}

	@Override
	public final void fillSourceFromTarget(Action source, ActionTO target) {
		throw new IllegalStateException("It's forbidden to convert an action TO to its model.");
	}
	
	public final List<ActionTO> convertSourceToTargetForIssue(List<Action> sources) {
		List<ActionTO> results = new ArrayList<>();
		
		for (Action source : sources) {
			results.add(convertSourceToTarget(source));
		}
		
		return results;
	}

	public final ActionTO convertSourceToTargetForIssue(Action source) {
		ActionTO target = createEmptyTarget();
		fillTargetFromSourceForIssue(target, source);
		return target;
	}

	public final void fillTargetFromSourceForIssue(ActionTO target, Action source) {
		target.setId(source.getId());
		target.setActionDate(source.getActionDate());
		target.setReason(source.getReason());
		target.setType(source.getActionType().getType());
		target.setUser(source.getUser());
	}
}
