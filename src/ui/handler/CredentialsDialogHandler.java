package ui.handler;

import java.awt.Color;

import javax.swing.border.LineBorder;

import org.apache.commons.lang3.StringUtils;

import ui.view.CredentialsDialog;

/**
 * Contains the logic behind {@link CredentialsDialog}.
 *
 * @author Alex Mikulinski
 * @since 06.04.2019
 */
public class CredentialsDialogHandler {
	private CredentialsDialog credentialsDialog;

	/**
	 * Creates a new {@link CredentialsDialogHandler} for a
	 * {@link CredentialsDialog}.
	 *
	 * @param _credentialsDialog
	 */
	public CredentialsDialogHandler(CredentialsDialog _credentialsDialog) {
		this.credentialsDialog = _credentialsDialog;
	}

	/**
	 * Checks whether the {@link CredentialsDialog#getTxtLogin() login} and
	 * {@link CredentialsDialog#getPasswordField() password} in this
	 * {@link #credentialsDialog} are not blank. If they are will mark both fields
	 * red. If everythings fine sets both {@link CredentialsDialog#setLogin} and
	 * {@link CredentialsDialog#setPassword}. After that closes this
	 * {@link #credentialsDialog}.
	 */
	public void handleLogin() {
		String	password;
		String	login;

		login		= this.credentialsDialog.getTxtLogin().getText();
		password	= new String(this.credentialsDialog.getPasswordField().getPassword());

		if (!this._goodCredentials(login, password)) {
			return;
		}

		this.credentialsDialog.setLogin(login);
		this.credentialsDialog.setPassword(password);

		password = null;

		this.credentialsDialog.dispose();
	}

	/**
	 * Closes this {@link #credentialsDialog}.
	 */
	public void handleCancel() {
		this.credentialsDialog.dispose();
	}

	private boolean _goodCredentials(String _login, String _password) {
		boolean goodCredentials;

		goodCredentials = true;

		if (StringUtils.isBlank(_login)) {
			this.credentialsDialog.getTxtLogin().setBorder(new LineBorder(Color.RED));
			goodCredentials = false;
		} else {
			this.credentialsDialog.getTxtLogin().setBorder(new LineBorder(Color.BLACK));
		}

		if (StringUtils.isBlank(_password)) {
			this.credentialsDialog.getPasswordField().setBorder(new LineBorder(Color.RED));
			goodCredentials = false;
		} else {
			this.credentialsDialog.getPasswordField().setBorder(new LineBorder(Color.BLACK));
		}

		return goodCredentials;
	}
}
