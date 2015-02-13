package ch.heigvd.ptl.sc.persistence;

import ch.heigvd.ptl.sc.model.IModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IRepository<MODEL extends IModel> extends MongoRepository<MODEL, String> {
}
