package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Contains methods for decoding and encoding dates.
 *
 * @author Alex Mikulinski
 * @since 09.07.2019
 */
public class DateHelper {
	/**
	 * Formats a passed {@link Date} to "dd.MM.yyyy HH:mm" format.
	 *
	 * @param _date
	 *           A {@link Date}.
	 * @return Formatted {@link Date} as {@link String}.
	 */
	public static String toString(Date _date) {
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

		return simpleDateFormat.format(_date);
	}

	/**
	 * The passed unix time stamp is converted to a "dd.MM.yyyy HH:mm" form.
	 *
	 * @param _timeStamp
	 *           The unix time stamp to convert.
	 * @return The time as {@link String}.
	 * @see <a href="https://api.stackexchange.com/docs/date">StackExchange Date
	 *      Formats</a>
	 */
	public static String toString(int _timeStamp) {
		return toString(toDate(_timeStamp));
	}

	/**
	 * The passed unix time stamp is converted to a {@link Date}.
	 *
	 * @param _timeStamp
	 *           The unix time stamp to convert.
	 * @return The time as {@link Date}.
	 * @see <a href="https://api.stackexchange.com/docs/date">StackExchange Date
	 *      Formats</a>
	 */
	public static Date toDate(int _timeStamp) {
		return Date.from(Instant.ofEpochSecond(_timeStamp));
	}

	/**
	 * Tries to parse the passed {@link String} to a {@link Date} and returns it.
	 * The {@link String} must contain the date in "dd.MM.yyyy HH:mm" format.
	 *
	 * @param _date
	 * @return The parsed {@link Date}.
	 */
	public static Date toDate(String _date) {
		SimpleDateFormat simpleDateFormat;

		simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

		try {
			return simpleDateFormat.parse(_date);
		} catch (ParseException e) {
			System.err.println("Failed parsing " + _date + " as a Date.");
			e.printStackTrace();
		}

		return null;
	}
}
