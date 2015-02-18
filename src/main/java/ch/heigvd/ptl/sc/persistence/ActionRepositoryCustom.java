package ch.heigvd.ptl.sc.persistence;

import ch.heigvd.ptl.sc.model.Action;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ActionRepositoryCustom {
	Action enrichedSave(Action action);
}
