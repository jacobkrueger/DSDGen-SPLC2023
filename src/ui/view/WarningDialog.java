package ui.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Provides a dialog to show a warning to a user.
 *
 * @author Alex Mikulinski
 * @since 07.04.2019
 */
public class WarningDialog extends JDialog {
	private static final long	serialVersionUID	= 1129225619148049809L;

	private boolean				canceled;

	/**
	 * Create a new {@link WarningDialog}.
	 *
	 * @param _text
	 *           The warning text as {@link String}.
	 */
	public WarningDialog(String _text) {
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, 450, 149);
		this.setTitle("Warning");

		this.canceled = false;

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				WarningDialog.this.canceled = true;
				WarningDialog.this.dispose();
			}
		});

		JPanel mainPanel;
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		this.setContentPane(mainPanel);
		this.getContentPane().setLayout(null);

		JButton okButton = new JButton("OK");
		okButton.setBounds(82, 76, 112, 23);
		mainPanel.add(okButton);
		okButton.addActionListener(a -> this.dispose());
		this.getRootPane().setDefaultButton(okButton);

		JTextArea txtrText = new JTextArea();
		txtrText.setEditable(false);
		txtrText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrText.setBounds(10, 11, 414, 54);
		txtrText.setLineWrap(true);
		txtrText.setWrapStyleWord(true);
		txtrText.setText(_text);
		mainPanel.add(txtrText);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(228, 76, 112, 23);
		btnCancel.addActionListener(a -> {
			this.canceled = true;
			this.dispose();
		});
		mainPanel.add(btnCancel);

		this.setLocationRelativeTo(null);

		this.setModalityType(ModalityType.APPLICATION_MODAL);

		this.setVisible(true);
	}

	/**
	 * Returns the {@link #canceled} of current {@link WarningDialog}.
	 *
	 * @return The {@link #canceled} as boolean.
	 */
	public boolean isCanceled() {
		return this.canceled;
	}
}
