package filter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Provides an abstraction for all filters of data.
 *
 * @author Alex Mikulinski
 * @since 11.07.2019
 */
public abstract class Filter {
	/**
	 * Applies a number of {@link Predicate}s on a {@link List} of data and returns
	 * the filtered {@link List}.
	 *
	 * @param _data
	 *           A {@link List} of data.
	 * @param _predicates
	 *           A number of {@link Predicate}s.
	 * @see IssueFilter
	 * @return A filtered {@link List} of data.
	 */
	@SafeVarargs
	public static <T> List<T> filter(List<T> _data, Predicate<T>... _predicates) {
		Predicate<T> predicates;

		// https://stackoverflow.com/a/24554095
		predicates = Arrays.stream(_predicates).reduce(Predicate::and).orElse(t -> true);

		return _data.stream().filter(predicates).collect(Collectors.toList());
	}
}
