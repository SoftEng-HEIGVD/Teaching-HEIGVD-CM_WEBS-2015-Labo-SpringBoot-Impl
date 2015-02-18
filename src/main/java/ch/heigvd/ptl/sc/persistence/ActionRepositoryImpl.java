package ch.heigvd.ptl.sc.persistence;

import ch.heigvd.ptl.sc.model.Action;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ActionRepositoryImpl implements ActionRepositoryCustom {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Action enrichedSave(Action action) {
		action.setActionDate(Calendar.getInstance().getTime());
		
		mongoTemplate.save(action);
		
		return mongoTemplate.findById(action.getId(), Action.class);
	}
}
