package filter;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.egit.github.core.Comment;

import model.Issue;

/**
 * Provides filtering methods and {@link Predicate}s for {@link List}s of
 * {@link Issue}s.
 *
 * @author Alex Mikulinski
 * @since 11.07.2019
 */
public class IssueFilter extends Filter {
	/**
	 * Finds {@link Issue}s which body, title or body of one of the comments
	 * contains a passed {@link String}.
	 *
	 * @param _text
	 *           A text as {@link String}.
	 * @return A {@link Predicate} for a filter.
	 */
	public static Predicate<Issue> contains(String _text) {
		return issue -> {
			if (issue.getBody().contains(_text)) {
				return true;
			}

			if (issue.getTitle().contains(_text)) {
				return true;
			}

			for (Comment comment : issue.getCommentList()) {
				if (comment.getBody().contains(_text)) {
					return true;
				}
			}

			return false;
		};
	}

	/**
	 * Finds {@link Issue}s which were <b>updated</b> before a specific
	 * {@link Date}.
	 *
	 * @param _date
	 *           A {@link Date}.
	 * @return A {@link Predicate} for a filter.
	 */
	public static Predicate<Issue> before(Date _date) {
		return issue -> issue.getUpdatedAt().before(_date);
	}
}
