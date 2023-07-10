package app;

import java.io.IOException;
import java.util.Date;

import ui.UIManager;
import ui.view.ProgressBar;
import utils.DateHelper;
import utils.FileManager;

/**
 * Main class for the Dynamic Software Documentation Generator.
 *
 * @author Alex Mikulinski
 * @since Jun 3, 2018
 */
public class DSDGen {
	// OPTIONS for debugging
	/** Whether to search only for active forks. Is faster. */
	public final static boolean	FIND_ACTIVE_FORKS	= false;

	/** Whether the program is in evaluation mode. */
	public final static boolean	EVALUATION			= false;
	/** Last Commit SHA-1 base */
	public final static String		SHA_COMMIT_BASE	= null;
	/** Last Commit SHA-1 fork */
	public final static String		SHA_COMMIT_FORK	= null;
	/** Max Date for Issues/PRs */
	public final static Date		DATE_MAX				= DateHelper.toDate("04.01.2015 11:29");

	/**
	 * Main entry point for this application.
	 *
	 * @param args
	 *           Program parameters.
	 * @throws IOException
	 *            If something goes wrong while executing.
	 */
	public static void main(String[] args) throws IOException {
		ProgressBar progressBar;

		progressBar = new ProgressBar("Initialising DSDGen", false);

		// Extract the resources
		if (!FileManager.exists(Settings.RESOURCES_PATH)) {
			FileManager.extractResource(Settings.RESOURCES_PATH, System.getProperty("user.dir"));
		}

		// Load settings
		Settings.load();

		// Create the output folder and extract needed resources to it
		if (!FileManager.exists(Settings.PAGE_OUTPUT.get())) {
			FileManager.createDirs(Settings.OUTPUT_RESOURCES_PATH.get());
			FileManager.copyDirectory(Settings.RESOURCES_PATH + Settings.TEMPLATE_RESOURCES.get(),
					Settings.OUTPUT_RESOURCES_PATH.get());
		}

		// Create the data folder
		if (!FileManager.exists(Settings.DATA_PATH.get())) {
			FileManager.createDirs(Settings.DATA_PATH.get());
		}

		// Finish progress bar and start the main GUI
		progressBar.finish();
		UIManager.getInstance().start();
	}

}
