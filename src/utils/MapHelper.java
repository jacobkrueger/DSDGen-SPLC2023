package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Provides helping methods for working with {@link Map}s.
 *
 * @author Alex Mikulinski
 * @since 18.07.2019
 */
public class MapHelper {
	/**
	 * Sorts a map by values in reversed order. From
	 * https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
	 *
	 * @param map
	 *           Map to sort.
	 * @return Sorted map.
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Collections.reverseOrder(Entry.comparingByValue()));

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
}
