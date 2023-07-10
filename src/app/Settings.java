package app;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Contains the settings for this project. Also contains methods for loading and
 * saving the settings to a configuration file.
 *
 * @author Alex Mikulinski
 * @since 06.03.2019
 */
public enum Settings {
	/** Contains the path to the databases. */
	DATA_PATH(""),

	/** Contains the path to the credentials for the GitHub API */
	GITHUB_CREDENTIALS(""),

	/** Contains the name of the examined software projects owner. */
	EPROJECT_OWNER(""),

	/** Contains the name of the examined software project. */
	EPROJECT_NAME(""),

	/** Contains the path to the examined software projects data. */
	EPROJECT_PATH(""),

	/** Contains the path to the examined software projects forks. */
	EPROJECT_FORKS(""),

	/** Contains the path to the examined software projects fork informations. */
	EPROJECT_FORK_INFO(""),

	/** Contains the name of a directory containing a repository. */
	REPOSITORY_DIR_NAME(""),

	/** Contains the path to the examined software projects repository. */
	EPROJECT_REPOSITORY_PATH(""),

	/** Contains the path to the examined software projects source directory. */
	EPROJECT_SRC_PATH(""),

	/** Contains the path to a software projects source directory. */
	SRC_PATH(""),

	/** Contains the name of a JSON file containing commits. */
	COMMIT_FILE_NAME(""),

	/** Contains the path to the StackOverflow database directory. */
	SO_PATH(""),

	/** Contains the name of a JSON file containing all Stack Overflow threads. */
	SO_FILE_NAME(""),

	/** Contains a list of tags with ";" as delimiter. */
	SO_TAGS(""),

	/** Contains the name of a JSON file containing issues. */
	ISSUES_FILE_NAME(""),

	/** Contains the name of a JSON file containing pull requests. */
	PULL_REQUEST_FILE_NAME(""),

	/**
	 * Contains the path to a .csv file with the call graph between methods and
	 * between classes.
	 */
	CALL_GRAPH_PATH(""),

	/**
	 * Contains the path to a .csv file with the inheritance relationships between
	 * classes.
	 */
	INHERITANCE_PATH(""),

	/** Contains the path to the template used in this programs output. */
	TEMPLATE_PATH(""),

	/**
	 * Contains the path to the resources for the template used in this programs
	 * output.
	 */
	TEMPLATE_RESOURCES(""),

	/** Contains the path to the html template for output page. */
	PAGE_TEMPLATE(""),

	/**
	 * Contains the path to the directory where the output page should be stored.
	 */
	PAGE_OUTPUT(""),

	/**
	 * Contains the path to the directory which stores the resources for the page
	 * output.
	 */
	OUTPUT_RESOURCES_PATH(""),

	/** The name for JSON file holding the inheritance graph. */
	INHERITANCE_JSON_NAME(""),

	/** The name for JSON file holding the call graph. */
	CALLS_JSON_NAME("");

	// Main resources
	/** Contains the path to the resources directory. */
	public static String	RESOURCES_PATH	= "resources" + File.separator;

	/** Contains the path to the config file. */
	public static String	CONFIG_PATH		= RESOURCES_PATH + "config.properties";

	/** The value hold by this enum. Most likely it will be a path to something. */
	private String			value;

	private Settings(String _value) {
		this.value = _value;
	}

	/**
	 * Loads the {@link #value}s for this {@link Settings} from the config file at
	 * {@link #CONFIG_PATH}. Replaces all "/" to current systems
	 * {@link File#separator}.
	 *
	 * @throws IOException
	 */
	public static void load() throws IOException {
		Configuration	config;
		String			tempValue;

		config = SettingsHelper.getInstance().getConfig();

		for (Settings setting : Settings.values()) {
			tempValue = config.getString(setting.toString());
			if (tempValue == null) {
				throw new IllegalArgumentException("Could not find a config for " + setting.get());
			}
			tempValue = tempValue.replaceAll("/", Matcher.quoteReplacement(File.separator));
			setting.set(tempValue);
		}
	}

	/**
	 * Returns the {@link Settings} enum with the passed name.
	 *
	 * @param _name
	 *           The enum name to search for.
	 * @return {@link Settings} entry if found.
	 */
	public static Settings get(String _name) {
		for (Settings setting : Settings.values()) {
			if (_name.equalsIgnoreCase(setting.toString())) {
				return setting;
			}
		}
		throw new IllegalArgumentException("No setting named " + _name + " found.");
	}

	/**
	 * Prints all settings to system output.
	 */
	public static void print() {
		for (Settings setting : Settings.values()) {
			System.out.println(setting.toString() + " = " + setting.get());
		}
	}

	/**
	 * Sets this to new value and saves this setting to the config.
	 *
	 * @param _newValue
	 *           The new value for this setting.
	 * @throws IOException
	 */
	public void save(String _newValue) throws IOException {
		this.set(_newValue);

		SettingsHelper.getInstance().getConfig().setProperty(this.toString(), _newValue);

		try {
			SettingsHelper.getInstance().getBuilder().save();
		} catch (ConfigurationException _e) {
			throw new IOException("Failed saving setting " + this.toString() + " to " + _newValue + ".", _e);
		}
	}

	/**
	 * Saves this to new value, saves to the config and reloads the config to
	 * resolve all config variables to new values.
	 *
	 * @param _newValue
	 *           The new value for this setting.
	 * @throws IOException
	 */
	public void saveAndReload(String _newValue) throws IOException {
		this.save(_newValue);
		load();
	}

	/**
	 * Returns the {@link #value} of current {@link Settings}.
	 *
	 * @return The {@link #value} as {@link String}.
	 */
	public String get() {
		return this.value;
	}

	/**
	 * Sets the {@link #value} of current {@link Settings}. <b>However does not save
	 * it to the config file!</b>
	 *
	 * @param value
	 *           The {@link #value} to set as {@link String}.
	 */
	public void set(String value) {
		this.value = value;
	}
}
