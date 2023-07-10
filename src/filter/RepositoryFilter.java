package filter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.Repository;

/**
 * Provides filtering methods and {@link Predicate}s for {@link List}s of
 * {@link Repository}s.
 *
 * @author Alex Mikulinski
 * @since 11.07.2019
 */
public class RepositoryFilter extends Filter {
	/**
	 * Finds {@link Repository}s which owner login contains a passed {@link String}.
	 *
	 * @param _text
	 *           A text as {@link String}.
	 * @return A {@link Predicate} for a filter.
	 */
	public static Predicate<Repository> owner(String _text) {
		return repository -> {
			if (StringUtils.containsIgnoreCase(repository.getOwner().getLogin(), _text)) {
				return true;
			}

			return false;
		};
	}

	/**
	 * Finds {@link Repository}s which updated date and created date differ in less
	 * than a minute, thus finds {@link Repository}s which are not just copies.
	 *
	 * @return A {@link Predicate} for a filter.
	 */
	public static Predicate<Repository> active() {
		return repository -> {
			Date	updated;
			Date	created;
			long	minutesDiff;

			updated		= repository.getUpdatedAt();
			created		= repository.getCreatedAt();

			minutesDiff	= ChronoUnit.MINUTES.between(Instant.ofEpochMilli(created.getTime()),
					Instant.ofEpochMilli(updated.getTime()));

			if (minutesDiff != 0) {
				return true;
			}

			return false;
		};
	}
}
