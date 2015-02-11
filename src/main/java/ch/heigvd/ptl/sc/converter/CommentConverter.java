package ch.heigvd.ptl.sc.converter;

import ch.heigvd.ptl.sc.model.Comment;
import ch.heigvd.ptl.sc.to.CommentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentConverter extends AbstractConverter<Comment, CommentTO> {
	@Autowired
	private UserConverter userConverter;
	
	@Override
	public Comment createEmptySource() {
		return new Comment();
	}

	@Override
	public CommentTO createEmptyTarget() {
		return new CommentTO();
	}

	@Override
	public void fillTargetFromSource(CommentTO target, Comment source) {
		target.setId(source.getId());
		target.setText(source.getDescription());
		target.setPostedOn(source.getPostedOn());
		target.setAuthor(userConverter.convertSourceToTarget(source.getAuthor()));
	}

	@Override
	public void fillSourceFromTarget(Comment source, CommentTO target) {
		source.setText(target.getText());
		source.setPostedOn(target.getPostedOn());
	}
}
