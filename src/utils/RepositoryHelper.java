package utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Repository;

/**
 * Contains methods which help working with {@link Repository Repositories}.
 *
 * @author Alex Mikulinski
 * @since 14.07.2019
 */
public class RepositoryHelper {
	/**
	 * Determines the grade of a repository recursively. The base has grade 0, a
	 * fork has grade 1, a fork of a fork has grade 2 etc.
	 *
	 * @param _repository
	 *           A {@link Repository}.
	 * @return The grade of passed {@link Repository}.
	 */
	public static int getGrade(Repository _repository) {
		Repository repository;

		repository = _repository;

		if (repository.getParent() == null) {
			return 0;
		} else {
			return 1 + getGrade(repository.getParent());
		}
	}

	/**
	 * Determines the ancestors of a repository recursively. The base has no
	 * ancestors, a fork has the base as ancestor etc.
	 *
	 * @param _repository
	 *           A {@link Repository}.
	 * @return A {@link List} of ancestors for the passed {@link Repository}.
	 */
	public static List<Repository> getAncestors(Repository _repository) {
		Repository			parent;
		List<Repository>	resultList;

		parent		= _repository.getParent();
		resultList	= new ArrayList<>();

		if (parent != null) {
			resultList.add(parent);
			if (parent.getParent() != null) {
				resultList.addAll(getAncestors(parent));
			}
		}

		return resultList;
	}

	/**
	 * Returns a {@link List} of URLs for the passed {@link Repository Repositories}
	 * as {@link String}.
	 *
	 * @param _repositories
	 *           A {@link List} of {@link Repository Repositories}.
	 * @return The URLs as {@link String}.
	 */
	public static String getURLs(List<Repository> _repositories) {
		List<Repository>	repositories;
		StringBuilder		stringBuilder;
		String				result;

		repositories	= _repositories;
		stringBuilder	= new StringBuilder();

		stringBuilder.append("[");

		repositories.forEach(a -> stringBuilder.append(a.getHtmlUrl().substring(8) + "; "));
		result	= stringBuilder.toString();

		result	= result.substring(0, result.length() - 2) + "]";
		return result;
	}

	public static int getCommitsAhead(Repository _repository1, Repository _repository2) {
		// TODO implement
		return 0;
	}

	public static int getCommitsBehind(Repository _repository1, Repository _repository2) {
		// TODO implement
		return 0;
	}

	public static int getCommitsAheadBehind(Repository _repository1, Repository _repository2) {
		// TODO implement
		return 0;
	}
}
