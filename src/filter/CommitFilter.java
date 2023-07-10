package filter;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import model.Commit;
import utils.DateHelper;

/**
 * Provides filtering methods and {@link Predicate}s for {@link List}s of
 * {@link Commit}s.
 *
 * @author Alex Mikulinski
 * @since 11.07.2019
 */
public class CommitFilter extends Filter {
	/**
	 * Finds {@link Commit}s which changed a specific file.
	 *
	 * @param _filePath
	 *           The path to the file as {@link String}.
	 * @return A {@link Predicate} for a filter.
	 */
	public static Predicate<Commit> changedFile(String _filePath) {
		return commit -> {
			for (String path : commit.getChangedFiles()) {
				if (_filePath.contains(path)) {
					return true;
				}
			}

			return false;
		};
	}

	/**
	 * Finds {@link Commit}s with a commit time before a specific {@link Date}.
	 *
	 * @param _date
	 *           A {@link Date}.
	 * @return A {@link Predicate} for a filter.
	 */
	public static Predicate<Commit> before(Date _date) {
		return commit -> DateHelper.toDate(commit.getCommitTime()).before(_date);
	}
}
