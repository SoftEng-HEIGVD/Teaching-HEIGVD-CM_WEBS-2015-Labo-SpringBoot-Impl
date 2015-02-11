package ch.heigvd.ptl.sc.converter;

import ch.heigvd.ptl.sc.model.IModel;
import ch.heigvd.ptl.sc.to.ITO;
import java.util.List;

public interface IConverter<SOURCE extends IModel, TARGET extends ITO> {
	List<TARGET> convertSourceToTarget(List<SOURCE> source);
	
	TARGET convertSourceToTarget(SOURCE source);
	
	List<SOURCE> convertTargetToSource(List<TARGET> target);
	
	SOURCE convertTargetToSource(TARGET target);
	
	SOURCE createEmptySource();
	
	void fillSourceFromTarget(SOURCE source, TARGET target);
	
	TARGET createEmptyTarget();
	
	void fillTargetFromSource(TARGET target, SOURCE source);
}
