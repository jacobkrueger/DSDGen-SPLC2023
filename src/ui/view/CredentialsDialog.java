package ui.view;

import javax.swing.JDialog;
import javax.swing.JTextField;

import ui.handler.CredentialsDialogHandler;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JCheckBox;

/**
 * Provides a dialog to get credentials from a user.
 *
 * @author Alex Mikulinski
 * @since 06.04.2019
 */
public class CredentialsDialog extends JDialog {
	private static final long			serialVersionUID	= -6019214826452562679L;

	private JPasswordField				passwordField;
	private JTextField					txtLogin;
	private JCheckBox						chckbxStore;
	private JButton						btnCancel;
	private JButton						btnLogin;
	private JLabel							lblWarning;

	private String							login;
	private String							password;

	private CredentialsDialogHandler	credentialsDialogHandler;

	/**
	 * Create a new {@link CredentialsDialog} with the title "Please log in to" +
	 * passed {@link String}.
	 *
	 * @param _target
	 *           The site the user should log into.
	 * @param _wrongCredentials
	 *           Whether to show a warning message that the last credentials were
	 *           wrong.
	 */
	public CredentialsDialog(String _target, boolean _wrongCredentials) {
		JPanel panel;
		panel									= new JPanel();

		this.credentialsDialogHandler	= new CredentialsDialogHandler(this);

		this.setContentPane(panel);

		this._addLabels();
		this._addInputFields();
		this._addButtons();
		this._addListeners();

		this.lblWarning.setVisible(_wrongCredentials);

		this.setTitle("Please log in to " + _target);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(100, 100, 367, 199);
		this.getContentPane().setLayout(null);
		this.getRootPane().setDefaultButton(this.btnLogin);

		this.setLocationRelativeTo(null);

		this.setModalityType(ModalityType.APPLICATION_MODAL);

		this.setVisible(true);
	}

	/**
	 * @return True if the user checked the {@link #chckbxStore}.
	 */
	public boolean isStoringAllowed() {
		return this.chckbxStore.isSelected();
	}

	private void _addInputFields() {
		this.txtLogin = new JTextField();
		this.txtLogin.setBounds(80, 38, 252, 20);
		this.getContentPane().add(this.txtLogin);

		this.passwordField = new JPasswordField();
		this.passwordField.setBounds(80, 69, 252, 20);
		this.getContentPane().add(this.passwordField);

		this.chckbxStore = new JCheckBox("Store credentials on local file system");
		this.chckbxStore.setBackground(Color.WHITE);
		this.chckbxStore.setBounds(80, 96, 252, 23);
		this.getContentPane().add(this.chckbxStore);
	}

	private void _addLabels() {
		this.lblWarning = new JLabel("Wrong credentials! Please try again!");
		this.lblWarning.setForeground(Color.RED);
		this.lblWarning.setBounds(80, 11, 252, 14);
		this.getContentPane().add(this.lblWarning);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogin.setBounds(10, 41, 66, 14);
		this.getContentPane().add(lblLogin);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 72, 66, 14);
		this.getContentPane().add(lblPassword);
	}

	private void _addButtons() {
		this.btnLogin = new JButton("Login");
		this.btnLogin.setBounds(80, 126, 89, 23);
		this.getContentPane().add(this.btnLogin);

		this.btnCancel = new JButton("Cancel");
		this.btnCancel.setBounds(243, 126, 89, 23);
		this.getContentPane().add(this.btnCancel);

	}

	private void _addListeners() {
		this.btnLogin.addActionListener(a -> this.credentialsDialogHandler.handleLogin());
		this.btnCancel.addActionListener(a -> this.credentialsDialogHandler.handleCancel());
	}

	/**
	 * Returns the {@link #passwordField} of current {@link CredentialsDialog}.
	 *
	 * @return The {@link #passwordField} as {@link JPasswordField}.
	 */
	public JPasswordField getPasswordField() {
		return this.passwordField;
	}

	/**
	 * Returns the {@link #txtLogin} of current {@link CredentialsDialog}.
	 *
	 * @return The {@link #txtLogin} as {@link JTextField}.
	 */
	public JTextField getTxtLogin() {
		return this.txtLogin;
	}

	/**
	 * Returns the {@link #chckbxStore} of current {@link CredentialsDialog}.
	 *
	 * @return The {@link #chckbxStore} as {@link JCheckBox}.
	 */
	public JCheckBox getChckbxStore() {
		return this.chckbxStore;
	}

	/**
	 * Returns the {@link #login} of current {@link CredentialsDialog}. If the user
	 * canceles the log in process, will return null.
	 *
	 * @return The {@link #login} as {@link String}.
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Sets the {@link #login} of current {@link CredentialsDialog}.
	 *
	 * @param login
	 *           The {@link #login} to set as {@link String}.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Returns the {@link #password} of current {@link CredentialsDialog}. If the
	 * user canceles the log in process, will return null.
	 *
	 * @return The {@link #password} as {@link String}.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the {@link #password} of current {@link CredentialsDialog}.
	 *
	 * @param password
	 *           The {@link #password} to set as {@link String}.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
