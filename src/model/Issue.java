package model;

import java.util.List;

import org.eclipse.egit.github.core.Comment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Provides a wrapper to combine {@link org.eclipse.egit.github.core.Issue}s
 * with it's {@link Comment}s.
 * 
 * @author Alex Mikulinski
 * @since 25.02.2019
 */
public class Issue extends org.eclipse.egit.github.core.Issue {
	/** serialVersionUID copied from {@link org.eclipse.egit.github.core.Issue} */
	private static final long	serialVersionUID	= 6358575015023539051L;

	List<Comment>					commentList;

	/**
	 * <b>Only for issues JSON parser</b>
	 */
	@JsonCreator
	private Issue(@JsonProperty("commentList") List<Comment> _commentList) {
		this.commentList = _commentList;
	}

	/**
	 * Create a new {@link Issue} from an {@link org.eclipse.egit.github.core.Issue}
	 * and it's {@link List} of {@link Comment}s.
	 * 
	 * @param _issue
	 *           The chosen {@link org.eclipse.egit.github.core.Issue}.
	 * @param _commentList
	 *           The {@link List} of {@link Comment}s for the chosen {@link Issue}.
	 */
	public Issue(org.eclipse.egit.github.core.Issue _issue, List<Comment> _commentList) {
		this._initialize(_issue);
		this.commentList = _commentList;
	}

	/**
	 * Copying a {@link org.eclipse.egit.github.core.Issue} to this.
	 * 
	 * @param _issue
	 *           The chosen {@link org.eclipse.egit.github.core.Issue}.
	 */
	private void _initialize(org.eclipse.egit.github.core.Issue _issue) {
		this.setAssignee(_issue.getAssignee());
		this.setBody(_issue.getBody());
		this.setBodyHtml(_issue.getBodyHtml());
		this.setBodyText(_issue.getBodyText());
		this.setClosedAt(_issue.getClosedAt());
		this.setComments(_issue.getComments());
		this.setCreatedAt(_issue.getCreatedAt());
		this.setHtmlUrl(_issue.getHtmlUrl());
		this.setId(_issue.getId());
		this.setLabels(_issue.getLabels());
		this.setMilestone(_issue.getMilestone());
		this.setNumber(_issue.getNumber());
		this.setPullRequest(_issue.getPullRequest());
		this.setState(_issue.getState());
		this.setTitle(_issue.getTitle());
		this.setUpdatedAt(_issue.getUpdatedAt());
		this.setUrl(_issue.getUrl());
		this.setUser(_issue.getUser());
	}

	/**
	 * Returns the {@link #commentList} of current {@link Issue}.
	 *
	 * @return The {@link #commentList} as {@link List}.
	 */
	public List<Comment> getCommentList() {
		return this.commentList;
	}

	/**
	 * Sets the {@link #commentList} of current {@link Issue}.
	 *
	 * @param comments
	 *           The {@link #commentList} to set as {@link List}.
	 */
	public void setCommentList(List<Comment> comments) {
		this.commentList = comments;
	}
}
