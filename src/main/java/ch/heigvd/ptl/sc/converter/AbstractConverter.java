package ch.heigvd.ptl.sc.converter;

import ch.heigvd.ptl.sc.model.IModel;
import ch.heigvd.ptl.sc.to.ITO;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConverter<SOURCE extends IModel, TARGET extends ITO> implements IConverter<SOURCE, TARGET> {
	@Override
	public final List<TARGET> convertSourceToTarget(List<SOURCE> sources) {
		List<TARGET> results = new ArrayList<>();
		
		for (SOURCE source : sources) {
			results.add(convertSourceToTarget(source));
		}
		
		return results;
	}

	@Override
	public final TARGET convertSourceToTarget(SOURCE source) {
		TARGET target = createEmptyTarget();
		fillTargetFromSource(target, source);
		return target;
	}
	
	@Override
	public final List<SOURCE> convertTargetToSource(List<TARGET> targets) {
		List<SOURCE> results = new ArrayList<>();
		
		for (TARGET target : targets) {
			results.add(convertTargetToSource(target));
		}
		
		return results;
	}

	@Override
	public final SOURCE convertTargetToSource(TARGET target) {
		SOURCE source = createEmptySource();
		fillSourceFromTarget(source, target);
		return source;
	}
}
