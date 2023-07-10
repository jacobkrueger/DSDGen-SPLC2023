package app;

import java.io.IOException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Provides helper methods for {@link Settings}. Used to load the configuration
 * file. Is a singleton to load the file only once.
 *
 * @author Alex Mikulinski
 * @since 17.03.2019
 */
public class SettingsHelper {
	private FileBasedConfigurationBuilder<FileBasedConfiguration>	builder;
	private Configuration														config;

	private static SettingsHelper												instance;

	/**
	 * @return The instance of this {@link SettingsHelper}.
	 * @throws IOException
	 */
	public static SettingsHelper getInstance() throws IOException {
		if (SettingsHelper.instance == null) {
			SettingsHelper.instance = new SettingsHelper();
		}
		return SettingsHelper.instance;
	}

	private SettingsHelper() throws IOException {
		Parameters params;

		params			= new Parameters();
		this.builder	= new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class);
		this.builder.configure(params.properties().setFileName(Settings.CONFIG_PATH));
		// TODO somehow fix this to enable lists in the config!
		// When enabling the list delimiter there is always an warning thrown on the
		// output
		// org.apache.commons.beanutils.FluentPropertyBeanIntrospector introspect
		// INFO: Error when creating PropertyDescriptor for public final void
		// org.apache.commons.configuration2.AbstractConfiguration.setProperty(java.lang.String,java.lang.Object)!
		// Ignoring this property.

		// this.builder.configure(params.properties().setFileName(Settings.CONFIG_PATH)
		// .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));

		try {
			this.config = this.builder.getConfiguration();
		} catch (ConfigurationException _cex) {
			throw new IOException("Failed loading configuration file.", _cex);
		}
	}

	/**
	 * Returns the {@link #builder} of current {@link SettingsHelper}.
	 *
	 * @return The {@link #builder} as {@link FileBasedConfigurationBuilder}.
	 */
	public FileBasedConfigurationBuilder<FileBasedConfiguration> getBuilder() {
		return this.builder;
	}

	/**
	 * Sets the {@link #builder} of current {@link SettingsHelper}.
	 *
	 * @param builder
	 *           The {@link #builder} to set as
	 *           {@link FileBasedConfigurationBuilder}.
	 */
	public void setBuilder(FileBasedConfigurationBuilder<FileBasedConfiguration> builder) {
		this.builder = builder;
	}

	/**
	 * Returns the {@link #config} of current {@link SettingsHelper}.
	 *
	 * @return The {@link #config} as {@link Configuration}.
	 */
	public Configuration getConfig() {
		return this.config;
	}

	/**
	 * Sets the {@link #config} of current {@link SettingsHelper}.
	 *
	 * @param config
	 *           The {@link #config} to set as {@link Configuration}.
	 */
	public void setConfig(Configuration config) {
		this.config = config;
	}

}
