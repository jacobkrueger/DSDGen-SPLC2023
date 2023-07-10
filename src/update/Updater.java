package update;

import java.io.IOException;

/**
 * Provides an interface to use for update classes for the database.
 *
 * @author Alex Mikulinski
 * @since Jan 21, 2019
 */
public interface Updater {

	/**
	 * Updates the chosen kind of database.
	 *
	 * @param _path
	 *           The target path.
	 * @throws IOException
	 *            If receiving, formatting or writing updates goes wrong.
	 */
	public void update(String _path) throws IOException;

	/**
	 * @param _target
	 *           The target that failed to update.
	 * @return A message that the target could not be updated and the user should
	 *         check their internet connection.
	 */
	public static String noInternetError(String _target) {
		return "Unable to update " + _target + ". Please check your internet connection.";
	}
}
