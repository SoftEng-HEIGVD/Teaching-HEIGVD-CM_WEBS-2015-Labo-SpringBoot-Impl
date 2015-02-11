package ch.heigvd.ptl.sc.model.persistence;

import ch.heigvd.ptl.sc.model.Issue;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

public class IssueRepositoryFactoryBean<R extends MongoRepository<Issue, String>>
	extends MongoRepositoryFactoryBean<R, Issue, String> {

	@Override
	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new IssueRepositoryFactory(operations);
	}

	private static class IssueRepositoryFactory extends MongoRepositoryFactory {
		private MongoOperations mongo;

		public IssueRepositoryFactory(MongoOperations mongoOperations) {
			super(mongoOperations);
			this.mongo = mongoOperations;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object getTargetRepository(RepositoryMetadata metadata) {
			TypeInformation<Issue> information = ClassTypeInformation.from((Class<Issue>) metadata.getDomainType());
			MongoPersistentEntity<Issue> pe = new BasicMongoPersistentEntity<>(information);
			MongoEntityInformation<Issue, String> mongometa = new MappingMongoEntityInformation<>(pe);

			return null;
//			return new IssueRepositoryImpl(mongometa, mongo);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return IssueRepository.class;
		}
	}
}
