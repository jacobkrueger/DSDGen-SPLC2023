package ui.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

/**
 * Provides a progress bar dialog.
 *
 * @author Alex Mikulinski
 * @since 14.04.2019
 */
public class ProgressBar extends JDialog {
	private static final long	serialVersionUID	= -5986796021841681308L;

	private JPanel					mainPanel;
	private int						progress;
	private JProgressBar			progressBar;
	private int						taskLength;
	private boolean				canceled;
	private boolean				cancelable;
	private boolean				isIndeterminate;
	private JButton				btnCancel;
	private String					message;

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ProgressBar progressBar;

		progressBar = new ProgressBar(100, "Loading stuff", true);

		for (int i = 0; i < 100; i++) {
			if (progressBar.isCanceled()) {
				return;
			}

			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}

			progressBar.step();
		}
	}

	/**
	 * Create a dialog with a indeterminate progressbar.
	 *
	 * @param _message
	 *           A task description.
	 * @param _cancelable
	 *           True if canceling this process by user is allowed.
	 */
	public ProgressBar(String _message, boolean _cancelable) {
		this.isIndeterminate	= true;
		this.cancelable		= _cancelable;
		this.message			= _message;

		this._start();
	}

	/**
	 * Create a dialog with a progressbar with set maximum task length.
	 *
	 * @param _taskLength
	 *           The maximum task length.
	 * @param _message
	 *           A task description.
	 * @param _cancelable
	 *           True if canceling this process by user is allowed.
	 * @wbp.parser.constructor
	 */

	public ProgressBar(int _taskLength, String _message, boolean _cancelable) {
		this.isIndeterminate	= false;
		this.taskLength		= _taskLength;
		this.cancelable		= _cancelable;
		this.progress			= 0;
		this.message			= _message;

		this._start();
	}

	private void _start() {
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		this.mainPanel = new JPanel();
		this.mainPanel.setBackground(Color.WHITE);
		this.setTitle("Please wait ...");
		this.setBounds(0, 10, 433, 161);
		this.mainPanel.setLayout(null);
		this.setContentPane(this.mainPanel);

		JLabel lblTask = new JLabel(this.message);
		lblTask.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTask.setBounds(10, 11, 399, 14);
		lblTask.setHorizontalAlignment(SwingConstants.CENTER);
		this.mainPanel.add(lblTask);

		if (this.cancelable) {
			this.btnCancel = new JButton("Cancel");
			this.btnCancel.setBounds(157, 88, 104, 23);
			this.btnCancel.addActionListener(l -> this.finish());
			this.mainPanel.add(this.btnCancel);
		}

		this.progressBar = new JProgressBar(0, this.taskLength);
		this.progressBar.setStringPainted(true);
		this.progressBar.setValue(this.progress);
		this.progressBar.setBounds(10, 47, 399, 23);
		this.progressBar.setIndeterminate(this.isIndeterminate);
		this.progressBar.setStringPainted(!this.isIndeterminate);
		this.mainPanel.add(this.progressBar);

		this.mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Make one step.
	 */
	public void step() {
		this.progress++;

		if (this.progress >= this.taskLength) {
			this.finish();
		}

		this.progressBar.setValue(this.progress);
	}

	/**
	 * Make the passed some steps.
	 *
	 * @param _step
	 *           The wanted amount of steps to make.
	 */
	public void step(int _step) {
		this.progress += _step;

		if (this.progress >= this.taskLength) {
			this.finish();
		}

		this.progressBar.setValue(this.progress);
	}

	/** Finishes and disposes this {@link ProgressBar}. */
	public void finish() {
		this.mainPanel.setCursor(null);
		this.canceled = true;
		this.dispose();
	}

	/**
	 * Returns the {@link #canceled} of current {@link ProgressBar}.
	 *
	 * @return The {@link #canceled} as boolean.
	 */
	public boolean isCanceled() {
		return this.canceled;
	}

}
