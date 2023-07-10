package filter;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.egit.github.core.PullRequest;

/**
 * Provides filtering methods and {@link Predicate}s for {@link List}s of
 * {@link PullRequest}s.
 *
 * @author Alex Mikulinski
 * @since 11.07.2019
 */
public class PullRequestFilter extends Filter {
	/**
	 * Finds {@link PullRequest}s which body or title contains a passed
	 * {@link String}.
	 *
	 * @param _text
	 *           A text as {@link String}.
	 * @return A {@link Predicate} for a filter.
	 */
	public static Predicate<PullRequest> contains(String _text) {
		return pullRequest -> {
			if (pullRequest.getBody() == null) {
				return false;
			}

			if (pullRequest.getBody().contains(_text)) {
				return true;
			}

			if (pullRequest.getTitle().contains(_text)) {
				return true;
			}

			return false;
		};
	}

	/**
	 * Finds {@link PullRequest}s which were <b>updated</b> before a specific
	 * {@link Date}.
	 *
	 * @param _date
	 *           A {@link Date}.
	 * @return A {@link Predicate} for a filter.
	 */
	public static Predicate<PullRequest> before(Date _date) {
		return pullRequest -> pullRequest.getUpdatedAt().before(_date);
	}
}
