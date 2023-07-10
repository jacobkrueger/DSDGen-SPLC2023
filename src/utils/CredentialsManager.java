package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Properties;

import ui.view.CredentialsDialog;

/**
 * Provides storing, loading credentials for a target and also getting them from
 * the user via console.
 *
 * @author Alex Mikulinski
 * @since 18.03.2019
 */
public class CredentialsManager {
	// Keys to store the credentials in the credentials file
	private final String	username_key	= "username";
	private final String	password_key	= "password";

	private String			username;
	private String			password;
	private String			target;
	private boolean		storeAllowed;

	/**
	 * Creates new {@link CredentialsManager}.
	 *
	 * @param _targetName
	 *           The name of the target to use the credentials for.
	 */
	public CredentialsManager(String _targetName) {
		this.target = _targetName;
	}

	/**
	 * Tries to get the credentials for the {@link #target} and stores them into
	 * {@link #username} and {@link #password}. <br>
	 * First tries to read the credentials from the passed file.<br>
	 * If that fails asks the user to type in the credentials via
	 * {@link CredentialsDialog}. The credentials can then be stored into the passed
	 * file.
	 *
	 * @param _credentialsPath
	 *           The path to the file holding/for storing the credentials.
	 * @param _wrongCredentials
	 *           Whether the last credentials were wrong.
	 * @throws IOException
	 */
	// public void getCredentials(String _credentialsPath, boolean
	// _wrongCredentials) throws IOException {
	// // Try to get the credentials from the file
	// if (this._getFromFile(_credentialsPath)) {
	// return;
	// }
	//
	// this._getFromGUI(_wrongCredentials);
	//
	// if (this.storeAllowed) {
	// this._storeToFile(_credentialsPath);
	// }
	// }
	/**
	 * Tries to get the credentials for the {@link #target} and stores them into
	 * {@link #username} and {@link #password}. <br>
	 * Asks the user to type in the credentials via {@link CredentialsDialog}. The
	 * credentials can then be stored into the passed file.
	 *
	 * @param _credentialsPath
	 *           The path to the file holding/for storing the credentials.
	 * @param _wrongCredentials
	 *           Whether the last credentials were wrong.
	 */
	public void getFromGUI(String _credentialsPath, boolean _wrongCredentials) {
		CredentialsDialog credentialsDialog;

		credentialsDialog	= new CredentialsDialog(this.target, _wrongCredentials);

		this.password		= credentialsDialog.getPassword();
		this.username		= credentialsDialog.getLogin();
		this.storeAllowed	= credentialsDialog.isStoringAllowed();

		if (this.storeAllowed) {
			this._storeToFile(_credentialsPath);
		}
	}

	/**
	 * Loads the credentials from the passed file. The password must be encoded
	 * using {@link Base64}.
	 *
	 * @param _filePath
	 *           The path to the file containing the credentials.
	 * @return True if credentials were retrieved, false otherwise.
	 */
	public boolean getFromFile(String _filePath) {
		Properties properties;

		properties = new Properties();

		try (FileInputStream input = new FileInputStream(_filePath)) {
			properties.load(input);
		} catch (IOException _e) {
			return false;
		}

		this.username	= properties.getProperty(this.username_key);
		this.password	= new String(Base64.getDecoder().decode(properties.getProperty(this.password_key)));

		return this.username != null && this.password != null;
	}

	/**
	 * Stores current credentials to the passed file. The password will be encoded
	 * using {@link Base64}.
	 *
	 * @param _filePath
	 *           The path to a file for storing the current credentials.
	 */
	private void _storeToFile(String _filePath) {
		Properties prop = new Properties();

		prop.setProperty(this.username_key, this.username);
		prop.setProperty(this.password_key, new String(Base64.getEncoder().encode(this.password.getBytes())));

		try (OutputStream output = new FileOutputStream(_filePath);) {
			prop.store(output, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the {@link #username} of current {@link CredentialsManager}.
	 *
	 * @return The {@link #username} as {@link String}.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Returns the {@link #password} of current {@link CredentialsManager}.
	 *
	 * @return The {@link #password} as {@link String}.
	 */
	public String getPassword() {
		return this.password;
	}
}
